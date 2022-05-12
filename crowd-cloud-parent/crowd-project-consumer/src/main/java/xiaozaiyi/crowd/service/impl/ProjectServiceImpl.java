package xiaozaiyi.crowd.service.impl;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.feign.IMysqlClientFeign;
import xiaozaiyi.crowd.feign.IRedisClientFeign;
import xiaozaiyi.crowd.service.ProjectService;
import xiaozaiyi.crowd.util.CustomUtils;
import xiaozaiyi.crowd.util.QOssUploadUtil;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.DetailProjectVO;
import xiaozaiyi.crowd.vo.MemberConfirmInfoVO;
import xiaozaiyi.crowd.vo.ProjectTypeVO;
import xiaozaiyi.crowd.vo.ProjectVO;

import javax.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-02   11:57
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private IRedisClientFeign iRedisClientFeign;

    @Autowired
    private IMysqlClientFeign iMysqlClientFeign;

    @Override
    public R<ProjectVO> fileUpload(List<MultipartFile> file) {
        if (file.isEmpty()) {
            return R.fail("文件不能为空");
        }
        ProjectVO projectVO;
        // 1.判断文件是单文件还是多文件
        if (file.size() == 1) {
            // 2.获取文件
            String resourceUrl = QOssUploadUtil.uploadResource(file.get(0));
            // 3.封装返回对象
            projectVO = new ProjectVO();
            // headerPicturePath 头图的路径
            projectVO.setHeaderPicturePath(resourceUrl);
        } else {
            // 3.获取多文件
            List<String> resourceUrlList = QOssUploadUtil.uploadResource(file);
            // 4.封装返回对象
            projectVO = new ProjectVO();
            // detailPicturePathList 详情图的路径
            projectVO.setDetailPicturePathList(resourceUrlList);

        }
        return R.data(projectVO);
    }

    @Override
    public R<ProjectVO> saveProject(ProjectVO projectVO, HttpServletRequest request) {

        // 1.获取token
        String id = CustomUtils.getJwt2Value(request);
        // 格式 project::33
        String redisKey = CustomConstant.TEMP_PROJECT_PREFIX + id;
        // 3.把对象转为String存入redis
        String projectStr = JSON.toJSONString(projectVO);
        R<String> stringR = iRedisClientFeign.setRedisKeyValueWithTimeout(redisKey, projectStr, CustomConstant.EXPIRED_TIME, TimeUnit.MINUTES);
        boolean success = stringR.isSuccess();
        return R.status(success);
    }

    @Override
    public R<MemberConfirmInfoVO> saveMemberConfirmInfo(MemberConfirmInfoVO memberConfirmInfoVO, HttpServletRequest request) {
        // 1.获取token解析 id
        String id = CustomUtils.getJwt2Value(request);
        // 格式 project::33
        String redisKey = CustomConstant.TEMP_PROJECT_PREFIX + id;
        // 3.从 redis 中获取对象
        R<String> stringR = iRedisClientFeign.getRedisValueByKey(redisKey);
        boolean success = stringR.isSuccess();
        // 检查 redis中是否是有效数据
        if (!success) {
            return R.fail(301, CustomConstant.SAVE_PROJECT_FAILED);
        }
        ProjectVO projectVO = JSON.parseObject(stringR.getData(), ProjectVO.class);

        // 4.设置数据
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        // 5.保存到数据库,并返回 project_id
        Integer memberId = Integer.valueOf(id);
        R<ProjectVO> saveEntity = iMysqlClientFeign.saveProject(projectVO, memberId);
        boolean result = saveEntity.isSuccess();
        if (!result) {
            log.error("项目 {} {}", CustomConstant.SAVE_FAILED, saveEntity.getMessage());
            R.fail(saveEntity.getMessage());
        }
        // 6.删除redis中的数据
        iRedisClientFeign.removeRedisValueByKey(redisKey);

        return R.status(result, saveEntity.getMessage());
    }

    @Override
    public R<List<ProjectTypeVO>> getProject() {
        R<List<ProjectTypeVO>> projectResult = iMysqlClientFeign.getProject();
        return projectResult;
    }

    @Override
    public R<DetailProjectVO> queryProjectDetail(Integer id) {
        R<DetailProjectVO> detailProjectVOR = iMysqlClientFeign.queryProjectDetail(id);
        boolean success = detailProjectVOR.isSuccess();
        if (!success) {
            return R.fail(detailProjectVOR.getMessage());
        }
        // 计算lastDate
        DetailProjectVO detailProjectVO = detailProjectVOR.getData();
        String deployDate = detailProjectVO.getDeployDate();
        // 计算 deploy_data时间
        Instant instant = Instant.ofEpochMilli(Long.parseLong(deployDate));
        // 加上众筹天数

        LocalDateTime TempLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        LocalDateTime realLocalDateTime = TempLocalDateTime.plusDays(detailProjectVO.getDay());
        // 获取当前时间
        LocalDate now = LocalDate.now();
        // 计算剩余天数
        Period p = Period.between(now, LocalDate.from(realLocalDateTime));
        // 设置天数
        Integer day = p.getYears() * 365 + p.getMonths() * 31 + p.getDays();
        detailProjectVO.setLastDate(day);
        return detailProjectVOR;
    }
}
