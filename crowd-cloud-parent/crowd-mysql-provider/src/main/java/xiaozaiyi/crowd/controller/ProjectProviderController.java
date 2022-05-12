package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.service.ProjectService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.DetailProjectVO;
import xiaozaiyi.crowd.vo.ProjectTypeVO;
import xiaozaiyi.crowd.vo.ProjectVO;

import java.util.List;

/**
 * 项目控制器
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-03   15:34
 */
@RestController
public class ProjectProviderController {
    @Autowired
    private ProjectService projectService;


    /**
     * 保存项目信息
     *
     * @param projectVO projectVO对象对象
     * @return 保存后的主键
     */
    @RequestMapping("/client/project/save")
    public R<ProjectVO> saveProject(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId) {
        R<ProjectVO> projectVOR = projectService.saveProject(projectVO, memberId);
        boolean success = projectVOR.isSuccess();
        return R.status(success, projectVOR.getMessage());
    }

    /**
     * 获取项目类型信息的列表
     *
     * @return
     */
    @RequestMapping("/client/project/type/get")
    public R<List<ProjectTypeVO>> getProject() {
        R<List<ProjectTypeVO>> projectTypeVOR = projectService.getProject();
        return projectTypeVOR;
    }

    @RequestMapping("/client/project/detail/get")
    R<DetailProjectVO> queryProjectDetail(@RequestParam(value = "id") Integer id){
        R<DetailProjectVO> detailProjectVOR = projectService.queryProjectDetail(id);
        return detailProjectVOR;
    }
}
