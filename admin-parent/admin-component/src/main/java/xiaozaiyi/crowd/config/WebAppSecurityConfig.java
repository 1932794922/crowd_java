package xiaozaiyi.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author : Crazy_August
 * @description : Security配置类
 * @Time: 2022-04-08   22:27
 */
@Configuration
//启动web环境下权限控制功能
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 关闭 csrf 认证
        http.authorizeRequests()   //对请求授权
                .antMatchers("/admin/login","/admin/**")
                .permitAll()   // 无条件访问
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                //允许跨域
                .cors();
    }


}
