package xiaozaiyi.crowd.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.entity.Auth;
import xiaozaiyi.crowd.entity.Role;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.mapper.AuthMapper;
import xiaozaiyi.crowd.service.AuthService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-08   12:17
 */
@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    public AuthMapper authMapper;

    @Override
    public  List<Auth> getAuthTree() {
        List<Auth> auths = authMapper.selectByExample(null);
        // 创建一个集合,用来存储 id 和 Menu 对象对应的关系便于查找父节点
        Map<Integer, Auth> AuthMap = new HashMap<>();
        //再次遍历给父节点填充子元素
        for (Auth auth : auths) {
            // 4.获取子节点的pid属性
            Integer pid = auth.getCategoryId();
            // 5.检测是否为null
            if (pid == null) {
                // 6.如果是null,说明是根节点
                Integer id = auth.getId();
                AuthMap.put(id, auth);
            }
        }
        // 7. 组装到父节点中
        for (Auth auth : auths) {
            Integer categoryId = auth.getCategoryId();
            if (categoryId != null) {
                AuthMap.get(categoryId).getChildren().add(auth);
            }
        }
        // 8. 返回根节点
        System.out.println(AuthMap);
        // 9. 返回根节点,把map转为list
        List<Auth> root = new ArrayList<>(AuthMap.values());
        return root;
    }

    @Override
    public boolean saveAuth(Auth auth) {
        // 获取当前权限所在的位置
        if (auth == null) {
            throw new CustomException(401, CrowdConstant.MENU_IS_NULL);
        }
        Integer categoryId = auth.getCategoryId();
        // categoryId 不为空,说明是子节点
        if (categoryId != null) {
            // 获取当前节点作为保存节点的categoryId 的 id
            // 要先讲插入的节点id赋值为null,否则会报错
            auth.setId(null);
            auth.setCategoryId(categoryId);
            // 说明不为根节点
            return authMapper.insert(auth) > 0;
        }
        Integer id = auth.getId();
        auth.setId(null);
        auth.setCategoryId(id);
        // 说明为根节点 pid 需要设置为 1
        return authMapper.insert(auth) > 0;
    }


    @Override
    public boolean updateAuth(Auth auth) {
        return authMapper.updateByPrimaryKey(auth) > 0;
    }

    @Override
    public boolean deleteAuth(Integer id) {
        return authMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Map<String, List<Auth>> getAuth(Integer id) {
        // 查询此用户没有的权限
        List<Auth> noAssignAuths = authMapper.selectNoAssignAuthyRoleId(id);
        // 查询此用户已经拥有所有的权限
        List<Auth> AssignAuths = authMapper.selectAssignAuthByRoleId(id);
        // 将两个集合装配到map中
        Map<String, List<Auth>> authMap = new HashMap<>();
        authMap.put("unChecked", noAssignAuths);
        authMap.put("checked", AssignAuths);
        return authMap;
    }

    @Override
    public boolean saveAdminRole(Integer id, List<Integer> ids) {
        int i = 0;
        // 1.删除原有的角色
        i = authMapper.deleteAuthRoleRelationsShip(id);
        // 2.添加新的角色
        if (ids != null && ids.size() > 0) {
            i = authMapper.insertAuthRoleRelationsShip(id, ids);
        }
        return i > 0;
    }

}
