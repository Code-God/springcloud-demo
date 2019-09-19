package com.example.department.feign;

import com.example.department.exception.KeepErrMsgConfiguration;
import com.example.department.exception.NotBreakerConfiguration;
//import com.example.department.hystrix.ServiceClientFallbackFactory;
import com.example.department.hystrix.FooConfiguration;
import com.example.department.hystrix.ServiceClientFallbackFactory;
import com.netflix.client.ClientException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "organization-server",path = "/test", fallbackFactory = ServiceClientFallbackFactory.class, configuration = {NotBreakerConfiguration.class})
@FeignClient(value = "organization-server",path = "/test", configuration = {FooConfiguration.class, KeepErrMsgConfiguration.class}) //FooConfiguration直接不熔断
public interface ServiceClient {

    @GetMapping("/get/hello")
    String sayHello(@RequestParam("name") String name);

    @GetMapping("/get/user")
    String getUser(@RequestParam("name") String name);

    @GetMapping("/save/user")
    String save(@RequestParam("name") String name);
}
