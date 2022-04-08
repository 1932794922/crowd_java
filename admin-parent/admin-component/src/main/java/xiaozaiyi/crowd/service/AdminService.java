package xiaozaiyi.crowd.service;

import com.github.pagehelper.PageInfo;
import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-03-30   14:26
 */
public interface AdminService {
    void saveAdmin(Admin admin);

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyWord, Integer pageNum, Integer pageSize);

    boolean deleteAdmin(Integer id);

    boolean updateAdmin(Admin admin);

    boolean addAdmin(Admin admin);

    boolean batchDeleteByIds(List<Integer> id);

    /**
     * 保存角色和权限的关系
     * @param id 角色id
     * @param ids 权限id
     * @return
     */
    boolean saveAdminRole(Integer id, List<Integer> ids);

}
