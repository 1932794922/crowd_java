package xiaozaiyi.crowd.service;

import com.github.pagehelper.PageInfo;
import xiaozaiyi.crowd.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    PageInfo<Role> getPageInfo(String keyWorks, Integer pageNum, Integer pageSize);

    boolean addRole(Role role);

    boolean updateRole(Role role);

    boolean batchDeleteByIds(List<Integer> ids);

    boolean deleteAdmin(Integer integer);


    /**
     * 获取角色列表
     * @param id
     * @return
     */
    Map<String, List<Role>> getAdminRole(Integer id);


}
