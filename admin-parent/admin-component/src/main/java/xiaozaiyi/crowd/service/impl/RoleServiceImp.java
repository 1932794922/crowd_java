package xiaozaiyi.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiaozaiyi.crowd.entity.Role;
import xiaozaiyi.crowd.mapper.RoleMapper;
import xiaozaiyi.crowd.service.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-04   18:54
 */
@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    public RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(String keyWorks, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleMapper.selectRoleByKeyWord(keyWorks);
        // 装配到pageInfo返回到controller层
        PageInfo<Role> rolePageInfo = new PageInfo<>(roles);
        return rolePageInfo;
    }

    @Override
    public boolean addRole(Role role) {
        // 返回成功条数
        return roleMapper.insert(role) > 0;
    }

    @Override
    public boolean updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role) > 0;
    }

    @Override
    public boolean batchDeleteByIds(List<Integer> ids) {
        return roleMapper.batchDeleteByIds(ids) > 0;
    }

    @Override
    public boolean deleteAdmin(Integer integer) {
        return roleMapper.deleteByPrimaryKey(integer) > 0;
    }

    @Override
    public Map<String, List<Role>> getAdminRole(Integer id) {
        // 查询此用户没有的所有角色
        List<Role> noAssignRoles = roleMapper.selectNoAssignRoleByAdminId(id);
        // 查询此用户已经拥有所有的角色
        List<Role> AssignRoles = roleMapper.selectAssignRoleByAdminId(id);
        // 将两个集合装配到map中
        Map<String, List<Role>> roleMap = new HashMap<>();
        roleMap.put("unChecked", noAssignRoles);
        roleMap.put("checked", AssignRoles);
        return roleMap;
    }

}
