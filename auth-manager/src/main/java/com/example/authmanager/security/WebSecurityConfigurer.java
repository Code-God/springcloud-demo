package com.example.authmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 配置OAuth2服务器验证用户
 * 为应用程序定义用户ID、密码和角色
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

//    /**
//     * configure方法是定义用户、密码和角色的地方
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }


    /**
     * authenticationManagerBean被Spring Security用来处理验证
     * 身份验证管理升级后不能直接注入。需要添加一下Bean
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http
//            .authorizeRequests()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin().and()
//            .httpBasic();
//    }


//    /**
//     * Spring Security使用UserDetailsService处理返回的用户信息，这些用户信息将由Spring Security返回。
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }
}
