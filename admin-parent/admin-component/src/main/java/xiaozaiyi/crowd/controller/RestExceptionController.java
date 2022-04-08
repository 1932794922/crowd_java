package xiaozaiyi.crowd.controller;


import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.util.ResultEntity;

/**
 * @author : Crazy_August
 * @description : 异常映射控制器
 * @Time: 2022-03-31   21:25
 */
@ControllerAdvice
public class RestExceptionController {

    /**
     * 自定义异常类
     * @param e CustomException 类  包含自定义状态码和自定义消息
     * @return 错误码 和 错误消息
     */
    @ExceptionHandler({CustomException.class})
    @ResponseBody
    public ResultEntity Failed(CustomException e) {
        return ResultEntity.error(e.getCode(), e.getMessage());
    }


    /**
     * 400错误
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    @ResponseBody
    public ResultEntity requestNotReadable(Exception e) {
        e.printStackTrace();
        return ResultEntity.error(400, "数据类型不匹配");
    }


    /**
     * 捕获404异常
     * @return
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public ResultEntity NoResourceException(NoHandlerFoundException e) {
        e.printStackTrace();
        return ResultEntity.error(404, "请求地址不存在");
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResultEntity server500(Exception e) {
        e.printStackTrace();
        return ResultEntity.error(500, e.getMessage());
    }

}
