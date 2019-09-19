package com.example.department.hystrix;

import com.example.department.feign.ServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * hystrix回退
 */
@Slf4j
@Component
public class ServiceClientFallbackFactory implements FallbackFactory<ServiceClient> {

    public static final String ERR_MSG = "接口暂时不可用";

    @Override
    public ServiceClient create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            log.error(msg);
        }
        return new ServiceClient() {
            @Override
            public String sayHello(String name) {
                return ERR_MSG;
            }

            @Override
            public String getUser(String name) {
                return ERR_MSG;
            }

            @Override
            public String save(String name) {
                return ERR_MSG;
            }
        };
    }
}
