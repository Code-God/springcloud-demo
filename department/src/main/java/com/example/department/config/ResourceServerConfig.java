package com.example.department.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/scope/**").access("#oauth2.hasScope('read') and hasRole('USER')") //作用域，scope路径的只有包含read的scope才可以访问  一般scope用来限制区分不同系统访问。比如移动和web只能访问一个
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//    }


//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.accessDeniedHandler((request, response, e)-> {
////            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
////            response.getWriter().write(new ObjectMapper().writeValueAsString(BaseResult.failure("授权失败")));
//        });
//        resources.authenticationEntryPoint((request,response,e)->{
////            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
////            response.getWriter().write(new ObjectMapper().writeValueAsString(BaseResult.failure("token失败")));
//        });
//    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers("/scope/**").access("#oauth2.hasScope('read') and hasRole('USER')") //作用域，scope路径的只有包含read的scope才可以访问  一般scope用来限制区分不同系统访问。比如移动和web只能访问一个
                .and()
                .authorizeRequests()
                .antMatchers("/aa/**").permitAll() //不需要校验权限aa的路径
                .anyRequest().authenticated()
                .and()
                .httpBasic();
//        http.addFilterBefore(customFilter, FilterSecurityInterceptor.class);
    }


}
