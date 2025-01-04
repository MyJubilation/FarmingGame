package com.beetles.PO.POJO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class History {
    private String id;
    private LocalDateTime createTime;
    private float money;
}
