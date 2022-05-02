package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.service.ProjectService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.ProjectVO;

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

    @PostMapping("/upload/image")
    public R<ProjectVO> fileUpload(List<MultipartFile> file) {
        R<ProjectVO> projectVOR =  projectService.fileUpload(file);
        boolean success = projectVOR.isSuccess();
        if (!success) {
            return R.fail(projectVOR.getMessage());
        }
        return projectVOR;
    }


}
