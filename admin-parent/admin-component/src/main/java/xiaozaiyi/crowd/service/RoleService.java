package xiaozaiyi.crowd.service;

import com.github.pagehelper.PageInfo;
import xiaozaiyi.crowd.entity.Role;

import java.util.List;

public interface RoleService {

    PageInfo<Role> getPageInfo(String keyWorks, Integer pageNum, Integer pageSize);

    boolean addRole(Role role);

    boolean updateRole(Role role);

    boolean batchDeleteByIds(List<Integer> ids);

    boolean deleteAdmin(Integer integer);
}
