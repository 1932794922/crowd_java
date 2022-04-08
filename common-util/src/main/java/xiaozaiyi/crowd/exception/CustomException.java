package xiaozaiyi.crowd.exception;

import lombok.Data;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-03-31   22:18
 */
@Data
public class CustomException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;

    private Integer code;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    protected CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
