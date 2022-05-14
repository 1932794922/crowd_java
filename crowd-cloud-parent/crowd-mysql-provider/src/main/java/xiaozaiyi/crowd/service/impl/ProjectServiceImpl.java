package xiaozaiyi.crowd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.mapper.MemberConfirmInfoMapper;
import xiaozaiyi.crowd.mapper.MemberLaunchInfoMapper;
import xiaozaiyi.crowd.mapper.ProjectMapper;
import xiaozaiyi.crowd.mapper.ProjectRelationShipMapper;
import xiaozaiyi.crowd.mapper.service.impl.ReturnServiceMapperImpl;
import xiaozaiyi.crowd.po.MemberConfirmInfoPO;
import xiaozaiyi.crowd.po.MemberLaunchInfoPO;
import xiaozaiyi.crowd.po.ProjectPO;
import xiaozaiyi.crowd.po.ReturnPO;
import xiaozaiyi.crowd.service.ProjectService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-03   19:53
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private MemberConfirmInfoMapper memberConfirmInfoMapper;

    @Autowired
    private ReturnServiceMapperImpl returnService;


    @Autowired
    private ProjectRelationShipMapper projectRelationShipMapper;

    @Autowired
    private MemberLaunchInfoMapper memberLaunchInfoMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public R<ProjectVO> saveProject(ProjectVO projectVO, Integer memberId) {
        try {
            // 1 把 projectVO 转为 projectPO
            ProjectPO projectPO = new ProjectPO();
            BeanUtils.copyProperties(projectVO, projectPO);

            // 2.设置memberId 存入projectVO对象,member表(发起人)
            projectPO.setMemberId(memberId);

            // 3.创建时间
            String createDate = String.valueOf(System.currentTimeMillis());
            projectPO.setCreateDate(createDate);
            projectPO.setDeployDate(createDate);

            // 一 : 插入项目和众筹人数据
            projectMapper.insert(projectPO);
            // 获取插入数据后的id
            Integer projectId = projectPO.getId();
            if (projectId == null) {
                return R.fail(CustomConstant.SAVE_FAILED);
            }
            //二、保存项目、分类的关联关系信息
            // 1.获取分类id
            List<Integer> typeIdList = projectVO.getTypeIdList();
            projectRelationShipMapper.insertRelationShipProjectWithType(typeIdList, projectId);
            //三、保存项目、标签的关联关系信息
            // 1.获取标签id
            List<Integer> tagIdList = projectVO.getTagIdList();
            projectRelationShipMapper.insertRelationShipProjectWithTag(tagIdList, projectId);
            // 四、保存项目中详情图片路径信息
            // 1.获取详情图片路径
            List<String> detailImageUrlList = projectVO.getDetailPicturePathList();
            projectRelationShipMapper.insertRelationShipProjectWithDetailImage(detailImageUrlList, projectId);
            // 五、保存项目发起人信息
            // 1.获取发起人信息
            MemberLaunchInfoVO memberLaunchInfoVO = projectVO.getMemberLaunchInfoVO();
            MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
            BeanUtils.copyProperties(memberLaunchInfoVO, memberLaunchInfoPO);
            memberLaunchInfoPO.setMemberId(memberId);
            memberLaunchInfoPO.setProjectId(projectId);
            memberLaunchInfoMapper.insert(memberLaunchInfoPO);
            //六、保存项目回报信息
            // 1.获取回报信息
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            // 2.先转为ReturnPO
            List<ReturnPO> returnPOList = new ArrayList<>();


            for (ReturnVO returnVO : returnVOList) {
                ReturnPO returnPO = new ReturnPO();
                returnPO.setProjectId(projectId);
                BeanUtils.copyProperties(returnVO, returnPO);
                returnPOList.add(returnPO);
            }

//        ProjectRelationShipMapper.insertRelationShipProjectWithReturn(returnPOList, projectId);
            // 使用这方法代替上面的方法
            returnService.saveBatch(returnPOList);
            // 七、保存项目确认信息
            // 1.获取确认信息
            MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
            // 2.转为 MemberConfirmInfoPO
            MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
            BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
            memberConfirmInfoPO.setMemberId(projectId);
            memberConfirmInfoMapper.insert(memberConfirmInfoPO);
            return R.success(CustomConstant.SAVE_SUCCESS);
        } catch (Exception e) {
            log.error("项目保存失败 {} ", e.getMessage());
            return R.fail(CustomConstant.SAVE_FAILED);
        }
    }

    @Override
    public R<List<ProjectTypeVO>> getProject() {
        try {
            List<ProjectTypeVO> projectTypeVOS = projectMapper.selectProjectTypeVOList();
            return R.data(projectTypeVOS);
        } catch (Exception e) {
            log.error("获取项目失败 {} ", e.getMessage());
            return R.fail(CustomConstant.GET_TYPE_PROJECT_FAILED);
        }
    }

    @Override
    public R<DetailProjectVO> queryProjectDetail(Integer id) {
        try {
            DetailProjectVO detailProjectVO = projectMapper.selectDetailProjectVOByProjectId(id);
            LambdaQueryWrapper<MemberLaunchInfoPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MemberLaunchInfoPO::getProjectId, id);
            MemberLaunchInfoPO memberLaunchInfoPO = memberLaunchInfoMapper.selectOne(queryWrapper);
            if (memberLaunchInfoPO != null) {
                MemberLaunchInfoVO memberLaunchInfoVO = new MemberLaunchInfoVO();
                BeanUtils.copyProperties(memberLaunchInfoPO,memberLaunchInfoVO);
                detailProjectVO.setMemberLaunchInfoVO(memberLaunchInfoVO);
            }
            return R.data(detailProjectVO);
        } catch (Exception e) {
            log.error("获取项目详情失败 {} ", e.getMessage());
            return R.fail(CustomConstant.GET_TYPE_PROJECT_DETAIL_FAILED);
        }
    }
}
