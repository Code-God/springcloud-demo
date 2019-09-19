package com.example.department.exception;

import com.example.department.utils.JsonUtils;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public class NotBreakerConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder(new HystrixException());
    }

//    /**
//     * 业务异常包装成 HystrixBadRequestException，不进入熔断逻辑,直接抛出异常
//     */
//    public class UserErrorDecoder implements ErrorDecoder {
//        private Logger logger = LoggerFactory.getLogger(getClass());
//
//        @Override
//        public Exception decode(String methodKey, Response response) {
//            Exception exception = null;
//            try {
//                // 获取原始的返回内容
//                String json = Util.toString(response.body().asReader());
//                exception = new RuntimeException(json);
//                // 将返回内容反序列化为Result，这里应根据自身项目作修改
//                ExceptionEntity result = JsonUtils.jsonToPojo(json, ExceptionEntity.class);
//                // 业务异常包装成 HystrixBadRequestException，不进入熔断逻辑
//                if (result.getCode() != 200) {
//                    exception = new HystrixException(result.getCode(), result.getMessage(), result.getServer());
//                }
//            } catch (IOException ex) {
//                logger.error(ex.getMessage(), ex);
//            }
//            return exception;
//        }
//    }
}
