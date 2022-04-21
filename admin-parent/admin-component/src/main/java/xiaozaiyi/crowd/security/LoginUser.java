package xiaozaiyi.crowd.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xiaozaiyi.crowd.entity.Admin;

import java.util.Collection;
import java.util.List;

/**
 * @author : Crazy_August
 * @description : 继承 admin 拓展权限 实现UserDetails接口
 * @Time: 2022-04-09   22:13
 */
@Data
public class LoginUser implements UserDetails {

    private Admin admin;

    //存储SpringSecurity所需要的权限信息和角色的集合
    private List<GrantedAuthority> permissions;


    public LoginUser() {
    }

    public LoginUser(List<GrantedAuthority> permissions) {
        this.permissions = permissions;
    }

    public LoginUser(Admin admin, List<GrantedAuthority> permissions) {
        this.admin = admin;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public String getPassword() {

        return admin.getUserPswd();
    }

    @Override
    public String getUsername() {
        return admin.getLoginAcct();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
