package com.example.organization.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Data;

@Data
public class HystrixException extends HystrixBadRequestException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String server;

    public HystrixException() {
        super("");
    }

    public HystrixException(int code, String message, String server) {
        super(message);
        this.code = code;
        this.server = server;
    }
}
