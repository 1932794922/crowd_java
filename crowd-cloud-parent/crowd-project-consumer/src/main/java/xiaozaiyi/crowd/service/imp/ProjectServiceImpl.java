package xiaozaiyi.crowd.service.imp;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.feign.IRedisClientFeign;
import xiaozaiyi.crowd.service.ProjectService;
import xiaozaiyi.crowd.util.CustomUtils;
import xiaozaiyi.crowd.util.QOssUploadUtil;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.ProjectVO;

import javax.servlet.http.HttpServletRequest;
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
//        // 1.获取token
//        String authorization = request.getHeader("authorization");
//        // 2.解析 token
//        String[] token = authorization.split(" ");
//        String Id;
//        try {
//            Claims claims = JwtUtil.parseJWT(token[1]);
//            Id = claims.getSubject();
//        } catch (Exception e) {
//            throw new CustomException(HttpStatus.SC_OK, CustomConstant.AUTHENTICATION_ERROR);
//        }
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
}
