package xiaozaiyi.crowd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.entity.AdminExample;
import xiaozaiyi.crowd.entity.Role;
import xiaozaiyi.crowd.exception.CustomException;
import xiaozaiyi.crowd.mapper.AdminMapper;
import xiaozaiyi.crowd.mapper.RoleMapper;
import xiaozaiyi.crowd.service.AdminService;
import xiaozaiyi.crowd.service.AuthService;
import xiaozaiyi.crowd.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Crazy_August
 * @description : 用户登录权限重新 springSecurity 类 ,要实现UserDetailsService  进行自定义密码校验
 * @Time: 2022-04-09   21:43
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public AdminMapper adminMapper;

    @Autowired
    public AdminService adminService;

    @Autowired
    public RoleService roleService;

    @Autowired
    public RoleMapper roleMapper;

    @Autowired
    public AuthService authService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1.根据用户名查询用户信息
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();

        criteria.andLoginAcctEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        if (admins.size() == 0 || admins.size() > 1) {
            //如果查询不到数据就通过抛出异常来给出提示,或者 存在多个用户
            throw new CustomException(401, CustomConstant.USERNAME_NOT_EXIST);
        }

        //封装成UserDetails对象返回
        Admin admin = admins.get(0);
        Integer id = admin.getId();

        // 2. 根据 admin 查询角色信息
        List<Role> adminRoles = roleMapper.selectAssignRoleByAdminId(id);

        // 3. 根据 admin id 查询权限信息
        List<String> authList = authService.getAssignAuthNameByAdminId(id);

        // 4. 创建集合对象存储 GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        // 5. 遍历 auth 存入权限信息
        for (Role role : adminRoles) {
            
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);

        }

        // 6. 遍历 auth 存入权限信息
//        for (String auth : authList) {
//            // 根据用户查询权限信息 添加到 LoginUser 中 getAuthorities
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(auth);
//            authorities.add(simpleGrantedAuthority);
//        }

        //6. 方式二: 把 authList 中字符串类型的权限信息转换成 GrantedAuthority对象存入 authorities中
        authorities = authList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 7. 封装到 LoginUser 中
        return new LoginUser(admin, authorities);
    }
}
