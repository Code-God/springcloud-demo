package com.example.organization.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public interface ServiceApi {

    @GetMapping("/get/hello")
    String sayHello(@RequestParam("name") String name);

    @GetMapping("/get/user")
    String getUser(@RequestParam("name") String name);

    @GetMapping("/save/user")
    String save(@RequestParam("name") String name);
}
