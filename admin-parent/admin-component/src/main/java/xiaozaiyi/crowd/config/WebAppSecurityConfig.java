package xiaozaiyi.crowd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xiaozaiyi.crowd.filter.JwtAuthenticationTokenFilter;


/**
 * @author : Crazy_August
 * @description : Security配置类
 * @Time: 2022-04-08   22:27
 */
@Configuration
//启动web环境下权限控制功能
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

//    @Autowired
//    private UserDetailsService UserDetailsServiceImpl;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 基于内存中的 账号密码
////        auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN");
//
//        // 数据库中查
//        auth.userDetailsService(UserDetailsServiceImpl);
//    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    //主要是通过 HttpSecurity 配置访问控制权限，
    protected void configure(HttpSecurity http) throws Exception {


        //1、关闭csrf，关闭Session
        http
                .csrf().disable()
                //允许跨域
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 关闭 csrf 认证
        http.authorizeRequests()   //对请求授权
                .antMatchers("/login")
                .anonymous()   // 无条件访问
                .anyRequest()
                .authenticated();

        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


        // 请求失败处理
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);


    }
}
