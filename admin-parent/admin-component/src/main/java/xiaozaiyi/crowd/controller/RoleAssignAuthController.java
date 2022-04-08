package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.entity.Auth;
import xiaozaiyi.crowd.entity.Role;
import xiaozaiyi.crowd.service.AdminService;
import xiaozaiyi.crowd.service.AuthService;
import xiaozaiyi.crowd.service.RoleService;
import xiaozaiyi.crowd.util.ResultEntity;

import java.util.List;
import java.util.Map;

/**
 * @author : Crazy_August
 * @description : 角色分配权限
 * @Time: 2022-04-08  15:20
 */
@RestController
public class RoleAssignAuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    public RoleService roleService;

    @Autowired
    public AuthService authService;

    @RequestMapping("role/assign")
    public ResultEntity getAdminRole(Integer id) {
        Map<String, List<Auth>> map = authService.getAuth(id);
        return ResultEntity.success().add("result", map);
    }

    @RequestMapping("role/assign/add")
    public ResultEntity addAdminRole(@RequestBody Map<String, Object> map) {
        Integer id = (Integer) map.get("id");
        List<Integer> ids = (List<Integer>) map.get("ids");
        boolean success =  authService.saveAdminRole(id, ids);
        if (!success) {
            return ResultEntity.success(100, CrowdConstant.UPDATE_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.UPDATE_SUCCESS);
    }


}

