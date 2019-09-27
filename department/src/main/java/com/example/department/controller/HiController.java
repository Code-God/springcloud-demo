package com.example.department.controller;

import com.example.department.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/say/hi")
    public String sayHi(String name) {
        return demoService.hiService(name);
    }

    @GetMapping("/save/user")
    public String save(String name) {
        return demoService.save(name);
    }

    @GetMapping("/get/hello")
//    @PreAuthorize("hasRole('ROLE_USER')")
    @PreAuthorize("hasAuthority('ROLE_USER')") //@PreAuthorize和@Secured，区别在于使用@Secured对应的角色必须要有ROLE_前缀。
    public String getHello() {
        return demoService.getHello();
    }

    @GetMapping("/scope/hello")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @PreAuthorize("#oauth2.hasScope('read')")
    public String getScopeHello() {
        return demoService.getHello();
    }
}
