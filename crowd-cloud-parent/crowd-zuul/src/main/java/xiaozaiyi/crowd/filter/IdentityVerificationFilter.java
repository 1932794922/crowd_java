package xiaozaiyi.crowd.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 身份验证网关过滤
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-26   15:03
 */
@Component
public class IdentityVerificationFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(IdentityVerificationFilter.class);

    /**
     * 读取配置文件中排除不需要授权的URL
     */
    @Value("${exclude.auth.url}")
    private String excludeAuthUrl;


    @Override
    public String filterType() {
        // 在访问前过滤
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 优先级为0，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {

        //路径与配置文件中的相匹配，则执行过滤
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestURI = ctx.getRequest().getRequestURI();
        List<String> excludesUrlList = Arrays.asList(excludeAuthUrl.split(","));

        boolean contains = excludesUrlList.contains(requestURI);

        // 是否执行该过滤器，此处为 true，说明需要过滤
        return !contains;


    }


    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("authorization");
        if (!StringUtils.hasText(token)) {
            //"token为空，禁止访问!"
            ctx.setSendZuulResponse(false);
//            ctx.setResponseBody(CustomConstant.NULL_TOKEN);
            throw new ZuulException(new RuntimeException(CustomConstant.ERROR_TOKEN),500, CustomConstant.NULL_TOKEN);
//            throw new RuntimeException(CustomConstant.ERROR_TOKEN);
        }
        // 如果带了 Bearer 并且是第一次访问
        if (token.startsWith("Bearer null")) {
            //"token格式不正确，禁止访问!"
            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            ctx.setResponseBody(CustomConstant.ERROR_TOKEN);
            throw new ZuulException(new RuntimeException(CustomConstant.ERROR_TOKEN),500,CustomConstant.ERROR_TOKEN);
//            throw new RuntimeException(CustomConstant.ERROR_TOKEN);
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

        return null;
    }
}
