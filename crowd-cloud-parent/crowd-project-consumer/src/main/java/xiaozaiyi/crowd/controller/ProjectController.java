package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.service.ProjectService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.DetailProjectVO;
import xiaozaiyi.crowd.vo.MemberConfirmInfoVO;
import xiaozaiyi.crowd.vo.ProjectTypeVO;
import xiaozaiyi.crowd.vo.ProjectVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 项目控制器
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-01   21:04
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PostMapping("/save")
    public R<ProjectVO> saveProject(@RequestBody ProjectVO projectVO, HttpServletRequest request) {
        R<ProjectVO> projectVOR = projectService.saveProject(projectVO, request);
        return R.status(projectVOR.isSuccess());
    }


    @PostMapping("/upload/image")
    public R<ProjectVO> fileUpload(List<MultipartFile> file) {
        R<ProjectVO> projectVOR = projectService.fileUpload(file);
        boolean success = projectVOR.isSuccess();
        if (!success) {
            return R.fail(projectVOR.getMessage());
        }
        return projectVOR;
    }

    @PostMapping("/save/confirm")
    public R<MemberConfirmInfoVO> saveMemberConfirmInfo(@RequestBody MemberConfirmInfoVO memberConfirmInfoVO, HttpServletRequest request) {
        R<MemberConfirmInfoVO> memberConfirmInfoVOR = projectService.saveMemberConfirmInfo(memberConfirmInfoVO, request);
        return R.status(memberConfirmInfoVOR.isSuccess(), memberConfirmInfoVOR.getCode(), memberConfirmInfoVOR.getMessage());
    }


    @GetMapping("/get/type/project")
    public R<List<ProjectTypeVO>> getProject() {
        R<List<ProjectTypeVO>> projectTypeVOR = projectService.getProject();
        boolean success = projectTypeVOR.isSuccess();
        if (!success) {
            R.fail(projectTypeVOR.getMessage());
        }
        return R.data(projectTypeVOR.getData());
    }


    @GetMapping("/query/project/detail")
    public R<DetailProjectVO> queryProjectDetail(@RequestParam(value = "id") Integer id) {
        R<DetailProjectVO> detailProjectVOR = projectService.queryProjectDetail(id);
        if (!detailProjectVOR.isSuccess()) {
            return R.fail(detailProjectVOR.getMessage());
        }
        return R.data(detailProjectVOR.getData());
    }

}
