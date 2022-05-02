package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.service.ReturnService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.ReturnVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 回报控制器
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-02   21:34
 */
@RestController
@RequestMapping("project/return")
public class ReturnController {

    @Autowired
    private ReturnService returnService;



    @PostMapping("/save")
    public R<ReturnVO> saveProject(@RequestBody List<ReturnVO> returnVO, HttpServletRequest request) {
        if (returnVO == null || returnVO.size() == 0) {
            return R.fail(CustomConstant.DATA_IS_NULL);
        }
        R<ReturnVO> returnVOR = returnService.saveReturn(returnVO,request);
        return R.status(returnVOR.isSuccess());
//
    }



    @PostMapping("/upload/image")
    public R<ReturnVO> fileUpload(List<MultipartFile> file) {
        R<ReturnVO> projectVOR = returnService.fileUpload(file);
        boolean success = projectVOR.isSuccess();
        if (!success) {
            return R.fail(projectVOR.getMessage());
        }
        return projectVOR;
    }



}
