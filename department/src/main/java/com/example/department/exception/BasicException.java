package com.example.department.exception;

import lombok.Data;

@Data
public class BasicException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code = 0;
    private String server;

    public BasicException() {
        super();
    }

    public BasicException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BasicException(int code, String message,String server) {
        super(message);
        this.code = code;
        this.server = server;
    }
}
