package com.beetles.PO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FarmInfoByNameVO {
    private int id;
    private String fieldName;
    private String fieldType;
    private int totalSize;
    private LocalDateTime createTime;
    private String animalName;
    private LocalDateTime birthTime;
    private int animalCount;
    private int life;
    private float income;
    private String imgUrl;
    private int size;

    private int rentDay;
}
