package com.example.organization.exception;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) //忽略类中不存在的字段
public class ExceptionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private int code;

    private String error;

    private String path;

    private String server;

    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date timestamp = new Date();
}
