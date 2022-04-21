package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.service.LoginService;
import xiaozaiyi.crowd.util.ResultEntity;

import java.util.Map;

/**
 *  账号登录退出相关
 * @author : Crazy_August
 * @description : 账号登录退出相关的
 * @Time: 2022-04-10   15:25
 */
@RestController
public class LoginController {

    @Autowired
    public LoginService loginService;


    @RequestMapping("login")
    public ResultEntity Login(@RequestBody Admin admin) {

        Map<String, Object> login = loginService.login(admin);
        String token = (String) login.get("token");
        String userName = (String) login.get("userName");

        return ResultEntity.success("登录成功").add("token", token)
                .add("userName",userName);
    }

    @RequestMapping("admin/logout")
    public ResultEntity Logout() {

        boolean success = loginService.logout();

        return  success ? ResultEntity.success(CustomConstant.LOGOUT_SUCCESS) :  ResultEntity.error(100, CustomConstant.LOGOUT_ERROR);
    }


}
