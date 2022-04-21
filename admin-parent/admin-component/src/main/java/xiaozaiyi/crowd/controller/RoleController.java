package xiaozaiyi.crowd.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.entity.Role;
import xiaozaiyi.crowd.service.RoleService;
import xiaozaiyi.crowd.util.ResultEntity;

import java.util.List;
import java.util.Map;

/**
 * @author : Crazy_August
 * @description : 角色维护
 * @Time: 2022-04-04   18:52
 */
@ResponseBody
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    public RoleService roleService;

    @RequestMapping("list")
    public ResultEntity getRole(@RequestParam(value = "keyWorks", defaultValue = "", required = false) String keyWorks,
                                @RequestParam(value = "pageNum", defaultValue = "", required = false) Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        PageInfo<Role> pageInfo = roleService.getPageInfo(keyWorks, pageNum, pageSize);
        return ResultEntity.success().add("record", pageInfo.getList())
                .add("pageNum", pageInfo.getPageNum())
                .add("pageSize", pageSize)
                .add("size", pageInfo.getSize())
                .add("total", pageInfo.getTotal());
    }

    @RequestMapping("add")
    @PreAuthorize("hasAuthority('role:add')")
    public ResultEntity addRole(@RequestBody Role role) {
        boolean success = roleService.addRole(role);
        if (!success) {
            return ResultEntity.error(100, CustomConstant.ADD_FAILED);
        }
        return ResultEntity.success(200, CustomConstant.ADD_SUCCESS);
    }


    @RequestMapping("update")
    @PreAuthorize("hasAuthority('role:update')")
    public ResultEntity updateRole(@RequestBody Role role) {
        boolean success = roleService.updateRole(role);
        if (!success) {
            return ResultEntity.error(100, CustomConstant.UPDATE_FAILED);
        }
        return ResultEntity.error(200, CustomConstant.UPDATE_SUCCESS);
    }

    @RequestMapping("delete")
    @PreAuthorize("hasAuthority('role:delete')")
    public ResultEntity dateRole(@RequestBody Map<String,  List<Integer>> map) {
        List<Integer> ids =  map.get("id");
        boolean success;
        // 判断是否是批量删除
        if (ids.size() > 1) {
            success = roleService.batchDeleteByIds(ids);
        } else {
            success = roleService.deleteAdmin(ids.get(0));
        }
        if (!success) {
            return ResultEntity.error(100, CustomConstant.DELETE_FAILED);
        }
        return ResultEntity.success(200, CustomConstant.DELETE_SUCCESS);
    }
}