package xiaozaiyi.crowd.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.exception.CustomException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 重写父类返回的结果
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-27   20:50
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    private final Logger logger = LoggerFactory.getLogger(MyErrorAttributes.class);

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        try {
            Throwable errorClass = getError(request);
            if (errorClass instanceof CustomException) {
                CustomException error = (CustomException) errorClass;
                String message = error.getMessage();
                Integer code = error.getCode();
                errorAttributes.put("message", message);
                if (Objects.equals(CustomConstant.NO_LOGIN_USER, message)) {
                    errorAttributes.put("success", false);
                } else {
                    errorAttributes.put("success", true);
                }
                errorAttributes.put("status", code);
            } else {
                ResponseStatusException responseError = (ResponseStatusException) errorClass;
                errorAttributes.put("message", errorClass.getMessage());
                errorAttributes.put("status", responseError.getStatus().value());
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            errorAttributes.put("status", 500);
            errorAttributes.put("message", e.getMessage());
        }
        return errorAttributes;
    }
}
