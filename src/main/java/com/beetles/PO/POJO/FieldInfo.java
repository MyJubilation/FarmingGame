package com.beetles.PO.POJO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FieldInfo {
    private int id;
    private String fieldName;
    private LocalDateTime createTime;
    private int size;
    private int rentDay;
}
