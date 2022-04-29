package xiaozaiyi.crowd.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.security.LoginUser;
import xiaozaiyi.crowd.util.JwtUtil;
import xiaozaiyi.crowd.util.RedisCache;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author : Crazy_August
 * @description :  Jwt 过滤器
 * @Time: 2022-04-10   20:33
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            //获取 前端发送的token
            String token = request.getHeader("authorization");
            if (!StringUtils.hasText(token)) {
                // 为空放行,有可能是第一次访问
                filterChain.doFilter(request, response);
                // 记得return
                return;
            }
            // 如果带了 Bearer 并且是第一次访问
            if (token.startsWith("Bearer null")) {
                // 为空放行,有可能是第一次访问
                filterChain.doFilter(request, response);
                // 记得return
                return;
            }

            String[] split = token.split(" ");
            // 解析 token
            String userId;
            try {
                Claims claims = JwtUtil.parseJWT(split[1]);
                userId = claims.getSubject();
            } catch (Exception e) {
                throw new CustomException(100, CustomConstant.ERROR_TOKEN);
            }
            // 从redis中获取用户信息
            String redisId = CustomConstant.REDIS_PREFIX + userId;
            LoginUser loginUser = redisCache.getCacheObject(redisId);
            if (Objects.isNull(loginUser)) {
                throw new CustomException(100, CustomConstant.NO_LOGIN_USER);
            }

            // 存入 SecurityContextHolder /当前是已认证的状态,存入 loginUser即可
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/server/filter").forward(request, response);
        }
    }
}
