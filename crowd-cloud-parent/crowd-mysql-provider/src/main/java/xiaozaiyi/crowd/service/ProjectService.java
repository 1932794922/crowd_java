package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.DetailProjectVO;
import xiaozaiyi.crowd.vo.ProjectTypeVO;
import xiaozaiyi.crowd.vo.ProjectVO;

import java.util.List;

public interface ProjectService  {

    /**
     *  保存项目
     *
     * @param projectVO
     * @return 方法主键 id
     */
    R<ProjectVO> saveProject(ProjectVO projectVO, Integer memberId);

    /**
     * 获取项目类型信息的列表
     *
     * @return
     */
    R<List<ProjectTypeVO>> getProject();

    /**
     * 获取项目详情
     * @param id 项目id
     * @return 项目详情
     */
    R<DetailProjectVO> queryProjectDetail(Integer id);
}
