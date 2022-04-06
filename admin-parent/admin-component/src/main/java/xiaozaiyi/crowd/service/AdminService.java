package xiaozaiyi.crowd.service;

import com.github.pagehelper.PageInfo;
import xiaozaiyi.crowd.entity.Admin;

import java.util.List;

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
}
