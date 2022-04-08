package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.entity.Auth;
import xiaozaiyi.crowd.service.AuthService;
import xiaozaiyi.crowd.util.ResultEntity;

import java.util.List;

/**
 * @author : Crazy_August
 * @description : 权限控制器
 * @Time: 2022-04-08   12:16
 */
@Controller
@ResponseBody
@RequestMapping("auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @RequestMapping("list")
    public ResultEntity getAuthTree(){
        List<Auth> auth = authService.getAuthTree();
        return ResultEntity.success().add("records",auth);
    }

    @RequestMapping("add")
    public ResultEntity addAuth(@RequestBody Auth menu) {
        boolean success = authService.saveAuth(menu);
        if (!success) {
            return ResultEntity.error(100, CrowdConstant.ADD_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.ADD_SUCCESS);
    }

    @RequestMapping("update")
    public ResultEntity updateAuth(@RequestBody Auth menu) {
        boolean success = authService.updateAuth(menu);
        if (!success) {
            return ResultEntity.error(100, CrowdConstant.UPDATE_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.UPDATE_SUCCESS);
    }

    @RequestMapping("delete")
    public ResultEntity deleteAuth(@RequestParam(value = "id", required = false) Integer id) {
        boolean success = authService.deleteAuth(id);
        if (!success) {
            return ResultEntity.error(100, CrowdConstant.DELETE_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.DELETE_SUCCESS);
    }


}
