package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    /**
     * 查询所有权限
     * @return
     */
    List<Auth> getAuthTree();

    boolean saveAuth(Auth menu);

    boolean updateAuth(Auth menu);

    boolean deleteAuth(Integer id);

    /**
     * 查询所有权限
     * @return
     */
    Map<String, List<Auth>> getAuth(Integer id);

    /**
     * 保存角色权限
     * @param id
     * @param ids
     * @return
     */
    boolean saveAdminRole(Integer id, List<Integer> ids);

    /**
     * 通过id查询已经拥有用户的权限
     * @param id
     * @return
     */
    List<String> getAssignAuthNameByAdminId(Integer id);
}
