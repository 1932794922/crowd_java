package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.entity.Admin;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-03-30   14:26
 */
public interface AdminService {
    void saveAdmin(Admin admin);

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);
}
