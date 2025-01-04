package com.beetles.PO.POJO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckLife {
    private int id;
    private int life;
    private LocalDateTime birthTime;
}
