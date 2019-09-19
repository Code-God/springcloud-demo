package com.example.department.controller;

import com.example.department.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
