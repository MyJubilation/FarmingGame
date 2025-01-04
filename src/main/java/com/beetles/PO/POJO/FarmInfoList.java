package com.beetles.PO.POJO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FarmInfoList {
    private String animalName;
    private String imgUrl;
    private int lastLife;
    private float income;
}
