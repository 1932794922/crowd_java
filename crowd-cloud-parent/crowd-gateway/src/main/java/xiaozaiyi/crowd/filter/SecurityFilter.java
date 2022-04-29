package xiaozaiyi.crowd.filter;

import io.jsonwebtoken.Claims;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.util.JwtUtil;

import java.util.List;

/**
 * 身份认证过滤
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-27   19:40
 */
@Component
public class SecurityFilter implements GlobalFilter, Ordered {

    private final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 读取配置文件中排除不需要授权的URL
     */
    @Value("${exclude.auth.path}")
    private String excludeAuthUrl;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.对于排除的 url 放行
        String path = exchange.getRequest().getURI().getPath();
        log.info(path);
        log.info(excludeAuthUrl);

        if (!StringUtils.isEmpty(excludeAuthUrl)) {
            String[] excludePaths = excludeAuthUrl.split(",");
            // 在排除的url放行
            for (String pattern : excludePaths) {
                if (pathMatcher.match(pattern, path)) {
                    return chain.filter(exchange);
                }
            }
        }
        // 需要过滤的请求
        boolean authorization = exchange.getRequest().getHeaders().containsKey("authorization");
        if (!authorization) {
            throw new CustomException(HttpStatus.SC_OK, CustomConstant.NULL_TOKEN);
        }
        List<String> authorizationList = exchange.getRequest().getHeaders().get("authorization");

        String token = authorizationList.get(0);

        if (!StringUtils.hasText(token)) {
            //"token为空，禁止访问!"
            throw new RuntimeException(CustomConstant.ERROR_TOKEN);
        }
        // 如果带了 Bearer 并且是第一次访问
        if (token.startsWith("Bearer null")) {
            //"token格式不正确，禁止访问!"
            throw new CustomException(HttpStatus.SC_OK, CustomConstant.ERROR_TOKEN);
        }

        String[] split = token.split(" ");
        // 解析 token
        String Id;
        try {
            Claims claims = JwtUtil.parseJWT(split[1]);
            Id = claims.getSubject();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.SC_OK, CustomConstant.ERROR_TOKEN);
        }
        // 从redis中获取用户信息
        String redisId = CustomConstant.REDIS_PREFIX + Id;

        // 获取 得到用户信息
        String accessTokenById = stringRedisTemplate.opsForValue().get(redisId);

        if (StringUtils.isEmpty(accessTokenById)) {
            throw new CustomException(HttpStatus.SC_OK, CustomConstant.NO_LOGIN_USER);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
