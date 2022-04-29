package xiaozaiyi.crowd.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.security.LoginUser;
import xiaozaiyi.crowd.service.LoginService;
import xiaozaiyi.crowd.util.JwtUtil;
import xiaozaiyi.crowd.util.RedisCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-10   15:57
 */
@Service
public class LoginUserImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private RedisCache RedisCache;


    @Override
    public Map<String, Object> login(Admin admin) {
        //  1. AuthenticationManager authenticationManager 进行用户认证

        String loginAcct = admin.getLoginAcct();
        String userPswd = admin.getUserPswd();

        // 2. 传入用户密码
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginAcct, userPswd);
        Authentication authenticate = authenticationManager.authenticate(authentication);

        // authenticate 为null 说明认证未通过
        if (Objects.isNull(authenticate)) {
            // 3. 如果认证没通过,给出提示
            throw new CustomException(100, CustomConstant.LOGIN_FAILED);
        }
        // 认证成功
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // 认证通过使用 id 生成 一个jwt返回给前端
        String id = loginUser.getAdmin().getId().toString();
        String jwt = JwtUtil.createJWT(id);

        Map<String, Object> map = new HashMap<>();
        map.put("token", jwt);
        map.put("userName", loginUser.getAdmin().getUserName());

        // 把完整的信息存入 redis ,格式  User::id :  loginUser
        RedisCache.setCacheObject(CustomConstant.REDIS_PREFIX + id, loginUser);

        return map;
    }

    @Override
    public boolean logout() {
        // 1. 获取 SecurityContextHolder 中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if (Objects.isNull(loginUser)) {
            throw new CustomException(CustomConstant.SYSTEM_ERROR_LOGOUT_ERROR);
        }
        Integer id = loginUser.getAdmin().getId();
        // 2. 删除 redis 的用户信息
        String redisId = CustomConstant.REDIS_PREFIX + id;
        RedisCache.deleteObject(redisId);
        // 3. 删除 SecurityContextHolder 授权的信息
        authentication.setAuthenticated(false);

        // 4. 返回成功
        return true;
    }
}
