package com.example.department.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.example.department.entity.Record;
import com.example.department.exception.BusinessException;
import com.example.department.feign.ServiceClient;
import com.example.department.mapper.UserMapper;
import com.example.department.service.DemoService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private ServiceClient serviceClient;
    @Autowired
    private UserMapper userMapper;

    @HystrixCommand(fallbackMethod = "hiFallback")
    @Override
    public String hiService(String name) {
        randomlyRunLong();
        return serviceClient.sayHello(name);
    }

    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3-1)+1)+1;
        if (randomNum == 3) {
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //降级服务处理，加入hystrix使用回调方法，使用注解方式时必须和调用服务在一个类中。
    public String hiFallback(String name) { //注意这里的参数要和上面的一样，否则找不到该方法
        return "hi service has a error!";
    }

    //    @HystrixCommand
    public Future<String> hiServiceAsync(String name) {
        return new AsyncResult<String>(name) {
            @Override
            public String get() {
                return serviceClient.sayHello(name);
            }
        };
    }

    private void threadAccess(String name) {
        try {
            int threadNum = 3;
            // 创建一个线程池
            ExecutorService exec = Executors.newFixedThreadPool(threadNum);
            // 定义一个任务集合
            List<Callable<String>> tasks = new ArrayList<Callable<String>>();
            Callable<String> task = null;

            // 确定每条线程的数据
            for (int i = 0; i < threadNum; i++) {
                task = new Callable<String>() {
                    @Override
                    public String call() {
                        String s = serviceClient.sayHello(name);
                        return s;
                    }
                };
                tasks.add(task);
            }
            exec.invokeAll(tasks);
            // 关闭线程池
            exec.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @LcnTransaction
    @Transactional
    @Override
    public String save(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("--------------------------------------"+authentication.getPrincipal());
//        String user = objectMapper.writeValueAsString(authentication.getPrincipal());

        Record record = new Record();
        record.setName(name);
        userMapper.insert(record);
        if (name.equalsIgnoreCase("报个错")) {
            throw new BusinessException(401, "报个错");
        }
//        record = null;
//        record.getId();
        String a = serviceClient.save(name);
        return String.valueOf(a);
    }

}
