package xiaozaiyi.crowd.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.entity.Menu;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.mapper.MenuMapper;
import xiaozaiyi.crowd.service.MenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Crazy_August
 * @description : 菜单服务实现类
 * @Time: 2022-04-06   14:35
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    /**
     * 暴力解法 方式一
     *
     * @return 返回根节点
     */
    @Override
    public Menu getAllMenu() {
        List<Menu> menusList = menuMapper.selectByExample(null);
        // 1.用来存储一个根节点
        Menu rootMenu = null;
        // 2.遍历所有的菜单
        for (Menu item : menusList) {
            // 3.获取pid属性
            Integer pid = item.getPid();
            //4.检测是否为null
            if (pid == null) {
                // 5.如果是null,说明是根节点
                rootMenu = item;
                continue;
            }
            // 6.如果不是null,说明不是根节点,说明是子节点,可以直接添加到父节点的children集合中
            // 7.再次遍历所有的菜单,找到子节点的父节点
            for (Menu maybeFather : menusList) {
                // 8.获取父节点的id
                Integer maybeFatherId = maybeFather.getId();
                // 9.检测父节点的id是否和子节点的pid相等
                if (Objects.equals(maybeFatherId, pid)) {
                    // 10.如果相等,说明找到了父节点,将子节点添加到父节点的children集合中
                    maybeFather.getChildren().add(item);
                    break;
                }
            }
        }
        // 11.返回根节点
        return rootMenu;
    }

    /**
     * 方式二
     *
     * @return 返回根节点
     */
    @Override
    public Menu getAllMenus() {
        List<Menu> menuList = menuMapper.selectByExample(null);
        // 1.用来存储一个根节点
        Menu rootMenu = null;
        // 创建一个集合,用来存储 id 和 Menu 对象对应的关系便于查找父节点
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 2.遍历所有的菜单
        for (Menu menu : menuList) {
            // 3.获取id属性
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }

        //再次遍历给父节点填充子元素
        for (Menu menu : menuList) {
            // 4.获取子节点的pid属性
            Integer pid = menu.getPid();
            // 5.检测是否为null
            if (pid == null) {
                // 6.如果是null,说明是根节点
                rootMenu = menu;
                continue;
            }
            // 7. 如果不是null,说明不是根节点,说明是子节点,可以直接添加到父节点的children集合中
            Integer childrenPid = menu.getPid();
            Menu pidMenu = menuMap.get(childrenPid);
            if (pidMenu == null) {
                pidMenu = new Menu();
            }
            // 8. 将子节点添加到父节点的children集合中
            pidMenu.getChildren().add(menu);
        }
        return rootMenu;
    }

    @Override
    public boolean saveMenu(Menu menu) {
        if (menu == null) {
            throw new CustomException(401, CrowdConstant.MENU_IS_NULL);
        }
        // 获取当前 被点击menu 的 id
        Integer id = menu.getId();
        Integer pid = menu.getPid();
        if (pid != null) {

            // 将被点击menu 的 id 作为保存的 pid
            menu.setPid(id);
            // 保存节点操作要先把id设置为 null
            menu.setId(null);
            // 说明不为根节点
            return menuMapper.insert(menu) > 0;
        }
        // 说明为根节点 pid 需要设置为 1
        menu.setPid(1);
        return menuMapper.insert(menu) > 0;
    }

    @Override
    public boolean updateMenu(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu) > 0;
    }

    @Override
    public boolean deleteMenu(Integer id) {
        return menuMapper.deleteByPrimaryKey(id) > 0;
    }
}
