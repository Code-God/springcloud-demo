package com.example.department.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("record")
public class Record {
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private String name;
}
