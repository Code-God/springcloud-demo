package com.example.department.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .csrf().disable();
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }


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

}
