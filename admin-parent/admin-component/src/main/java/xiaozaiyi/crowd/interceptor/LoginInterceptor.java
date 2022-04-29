package xiaozaiyi.crowd.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Crazy_August
 * @description : 登录信息拦截
 * @Time: 2022-04-03   14:54
 */
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("Authorization");
//        if ("Bearer null".equals(authorization) || authorization == null) {
//            throw new CustomException(401, CrowdConstant.NO_AUTHORIZATION);
//        }
        return true;
    }

}
