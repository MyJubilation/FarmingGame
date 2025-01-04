package com.beetles.PO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnimalAddDTO {
    private String fieldName;
    private String animalName;
    private int count;
    private LocalDateTime birthTime;
    private int animalStatus;
}
