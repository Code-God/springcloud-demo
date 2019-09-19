package com.example.organization.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.organization.api.ServiceApi;
import com.example.organization.entity.User;
import com.example.organization.exception.BusinessException;
import com.example.organization.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class ServiceImpl implements ServiceApi {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String sayHello(String name) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes == null ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
        log.info("----------------1->"+request.getHeader("Authorization"));
        log.info("----------------2---lcn->"+request.getHeader("tx-group"));
        return "hello-> " + name;
    }

    @Override
    public String getUser(String name) {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        return "success";
    }

    @LcnTransaction //分布式事务注解
    @Transactional  //本地事务注解
    @Override
    public String save(String name) {
        if (name.equalsIgnoreCase("黑名单")) {
            throw new BusinessException(402, "这是一个是黑名单，我抛个错误给你");
        }else if(name.equalsIgnoreCase("空指针")){
            User user = null;
            user.setName(name);
        } else if (name.equalsIgnoreCase("转换错误")){
            String a = "a";
            int b = Integer.parseInt(a);
        } else{
            User user = new User();
            user.setAge(18);
            user.setName(name);
            user.setEmail("test@email.com");
            userMapper.insert(user);
        }
        return "success";
    }
}
