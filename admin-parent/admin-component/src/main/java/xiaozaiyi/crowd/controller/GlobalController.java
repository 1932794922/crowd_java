package xiaozaiyi.crowd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.util.ResultEntity;

/**
 * @author : Crazy_August
 * @description : 全局控制器
 * @Time: 2022-04-07   10:08
 */
@ResponseBody
@Controller
public class GlobalController {

    /**
     * 映射资源不存在
     * @return
     */
    @RequestMapping("/")
    public ResultEntity index() {
        throw  new CustomException(404,CrowdConstant.MESSAGE_ERROR_404);
//        return ResultEntity.error(404, CrowdConstant.MESSAGE_ERROR_404);
    }

    /**
     * 映射资源不存在
     * @return
     */
    @RequestMapping("/server/error")
    public ResultEntity serverError() {
        throw new CustomException(500,CrowdConstant.SERVER_ERROR);
//        return ResultEntity.error(500, CrowdConstant.SERVER_ERROR);
    }


}
