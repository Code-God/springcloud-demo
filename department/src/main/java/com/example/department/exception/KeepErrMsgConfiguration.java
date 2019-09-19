package com.example.department.exception;

import com.example.department.utils.JsonUtils;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * Feign 会把httpstatus不是200的进行拦截
 */
public class KeepErrMsgConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder(new BasicException());
    }

//    /**
//     * 提取出里面的message信息，然后抛出RuntimeException，这样当进入到熔断方法中时，获取到的异常就是我们处理过的RuntimeException。
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
//                String message = result.getMessage() == null ? "网络繁忙" : result.getMessage();
//                // 业务异常抛出简单的 RuntimeException，保留原来错误信息 .
//                if (result.getCode() != 200) {
//                    exception = new BasicException(result.getCode(), message, result.getServer());
//                }
//            } catch (IOException ex) {
//                logger.error(ex.getMessage(), ex);
//            }
//            return exception;
//        }
//    }
}
