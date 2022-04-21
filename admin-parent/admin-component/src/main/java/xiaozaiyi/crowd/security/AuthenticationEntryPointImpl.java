package xiaozaiyi.crowd.security;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.util.CustomUtils;
import xiaozaiyi.crowd.util.ResultEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResultEntity result = new ResultEntity(HttpStatus.UNAUTHORIZED.value(), CustomConstant.AUTHENTICATION_ERROR);
        String json = JSON.toJSONString(result);
        CustomUtils.renderString(response, json);
    }
}