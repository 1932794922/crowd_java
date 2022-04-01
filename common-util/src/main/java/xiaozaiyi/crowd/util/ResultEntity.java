package xiaozaiyi.crowd.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求处理数据工具类
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-03-30   22:38
 */
@Data
public class ResultEntity {
    /**
     * 返回客户端统一格式，包括状态码，提示信息，以及业务数据
     */
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer ERRORS_CODE = 100;

    // 状态码 200成功  100失败
    private Integer code;

    //请求失败的错误信息
    private String message;

    private Map<String, Object> data = new HashMap<String, Object>();

    public ResultEntity() {
    }

    public ResultEntity(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultEntity(int code, String message, HashMap<String, Object> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultEntity success() {
        return new ResultEntity(SUCCESS_CODE, SUCCESS);
    }
    public static ResultEntity success(String message) {
        return new ResultEntity(SUCCESS_CODE, message);
    }

    public static ResultEntity success(Integer code, String message) {
        return new ResultEntity(code, message);
    }

    public static ResultEntity error() {
        return new ResultEntity(ERRORS_CODE, ERROR);
    }

    public static ResultEntity error(Integer code, String message) {
        return new ResultEntity(code, message);
    }


    public ResultEntity add(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }

}
