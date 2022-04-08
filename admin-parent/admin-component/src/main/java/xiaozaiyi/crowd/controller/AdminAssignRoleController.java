package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.entity.Role;
import xiaozaiyi.crowd.service.AdminService;
import xiaozaiyi.crowd.service.RoleService;
import xiaozaiyi.crowd.util.ResultEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Crazy_August
 * @description : 用户角色分配权限
 * @Time: 2022-04-07   14:17
 */
@RestController
public class AdminAssignRoleController {

    @Autowired
    private AdminService adminService;

    @Autowired
    public RoleService roleService;


    @RequestMapping("admin/assign")
    public ResultEntity getAdminRole(Integer id) {
        Map<String, List<Role>> map = roleService.getAdminRole(id);

        return ResultEntity.success().add("result", map);
    }

    @RequestMapping("admin/assign/add")
    public ResultEntity addAdminRole(@RequestBody Map<String, Object> map) {
        Integer id = (Integer) map.get("id");
        List<Integer> ids = (List<Integer>) map.get("ids");
        boolean success =  adminService.saveAdminRole(id, ids);
        if (!success) {
            return ResultEntity.success(100, CrowdConstant.UPDATE_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.UPDATE_SUCCESS);
    }


}

