package com.example.organization.exception;

import com.example.organization.utils.JsonUtils;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private RuntimeException e;

    public FeignErrorDecoder(RuntimeException e) {
        this.e = e;
    }

    @Override
    public Exception decode(String s, Response response) {
        Exception exception = null;
        try {
            // 获取原始的返回内容
            String json = Util.toString(response.body().asReader());
            exception = new RuntimeException(json);
            // 将返回内容反序列化为Result，这里应根据自身项目作修改
            ExceptionEntity result = JsonUtils.jsonToPojo(json, ExceptionEntity.class);
            String message = result.getMessage() == null ? "网络繁忙" : result.getMessage();
            // 业务异常抛出简单的 RuntimeException，保留原来错误信息 .
            if (result.getCode() != 200) {
                if (e instanceof HystrixException) {
                    exception = new HystrixException(result.getCode(), result.getMessage(), result.getServer());
                } else {
                    exception = new BasicException(result.getCode(), message, result.getServer());
                }
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return exception;
    }
}
