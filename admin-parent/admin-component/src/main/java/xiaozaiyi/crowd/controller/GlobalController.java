package xiaozaiyi.crowd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.util.ResultEntity;

import javax.servlet.http.HttpServletRequest;

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
     *
     * @return
     */
    @RequestMapping("/")
    public ResultEntity index() {
        throw new CustomException(404, CustomConstant.MESSAGE_ERROR_404);
    }

    /**
     * 服务器异常
     *
     * @return
     */
    @RequestMapping("/server/error")
    public ResultEntity serverError() {
        throw new CustomException(500, CustomConstant.SERVER_ERROR);
    }

    /**
     * 403 - 被禁止
     *
     * @return
     */
    @RequestMapping("/server/refuse")
    public ResultEntity serverRefuse() {
        throw new CustomException(403, CustomConstant.SERVER_REFUSE);
    }


    /**
     * 处理在过滤器中抛出的异常
     *
     * @return
     */
    @RequestMapping("/server/filter")
    public ResultEntity filterRefuse(HttpServletRequest request) {
        // 里面封装异常信息
        CustomException errorMessage = (CustomException) request.getAttribute("exception");
        throw new CustomException(errorMessage.getCode(), errorMessage.getMessage());
    }


}
