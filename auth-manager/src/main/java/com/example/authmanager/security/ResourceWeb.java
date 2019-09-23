package com.example.authmanager.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/oauth")
public class ResourceWeb {

    @GetMapping("/user")
    public Principal user(Principal principal) {
        //获取当前用户信息
        return principal;
    }
}
