package com.example.authmanager.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.authmanager.entity.User;
import com.example.authmanager.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 自定义的身份验证逻辑
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("name", username));
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        User user = users.get(0);
//        user.isAccountNonLocked(); //关闭锁定
//        user.isEnabled();
//        user.isAccountNonExpired();
//        user.isCredentialsNonExpired();
        return user;
    }


}
