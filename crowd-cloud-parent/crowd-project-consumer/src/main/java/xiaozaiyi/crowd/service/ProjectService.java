package xiaozaiyi.crowd.service;

import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.DetailProjectVO;
import xiaozaiyi.crowd.vo.MemberConfirmInfoVO;
import xiaozaiyi.crowd.vo.ProjectTypeVO;
import xiaozaiyi.crowd.vo.ProjectVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-02   11:56
 */
public interface ProjectService {
    /**
     * 上传资源
     *
     * @param file
     * @return 地址列表
     */
    R<ProjectVO> fileUpload(List<MultipartFile> file);

    /**
     * 创建项目
     *
     * @param projectVO
     * @return
     */
    R<ProjectVO> saveProject(ProjectVO projectVO, HttpServletRequest request);

    /**
     * 保存确认信息
     *
     * @param memberConfirmInfoVO
     * @return
     */
    R<MemberConfirmInfoVO> saveMemberConfirmInfo(MemberConfirmInfoVO memberConfirmInfoVO,HttpServletRequest request);


    /**
     * 获取项目类型信息
     *
     * @return
     */
    R<List<ProjectTypeVO>> getProject();

    /**
     * 获取项目详情
     * @param id
     * @return
     */
    R<DetailProjectVO> queryProjectDetail(Integer id);
}
