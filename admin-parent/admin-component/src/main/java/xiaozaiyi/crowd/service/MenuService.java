package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.entity.Menu;

/**
 * 菜单服务接口
 * @author xiaozaiyi
 * @version 1.0
 * @date 2020/7/23
 * @since 1.8
 */
public interface MenuService {
    /**
     * 获取所有菜单 方式一:暴力解法
     * @return
     */
    Menu getAllMenu();

    /***
     *  对方式一的改进
     * @return
     */
    Menu getAllMenus();

    boolean saveMenu(Menu menu);

    boolean updateMenu(Menu menu);

    boolean deleteMenu(Integer id);
}
