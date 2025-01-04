package com.beetles.service;

import com.beetles.PO.AnimalAddDTO;
import com.beetles.PO.FarmInfoByNameVO;
import com.beetles.PO.LoginDTO;
import com.beetles.PO.POJO.*;

import java.time.LocalDateTime;
import java.util.List;

public interface FarmService {
    List<String> getFieldsName();

    int login(LoginDTO loginDTO);

    List<FarmInfoByNameVO> getFarmInfoByName(String field_name);

    int changeFieldName(String fieldName, String changedName);

    int selectFarmByName(String changedName);

    int getLastLifeByBirthTime(LocalDateTime birthTime, int life, int id);

    SizeInfo getSize(String fieldName);

    int checkLife();

    float checkIncome();

    List<Animal> getAnimalInfo();

    int getSizeTotalAdd(String animalName, int count);

    int animalAdd(AnimalAddDTO animalAddDTO);

    int checkMoney(AnimalAddDTO animalAddDTO);

    int fieldExpand(FieldExpand fieldExpand);

    int checkTimeOut();

    void setDefaultfield();

    List<History> checkHistory();

    int fieldBorrow(FieldBrrow fieldBrrow);

    int checkField();

    int getFarmRemainDayByName(String fieldName);

    void setHistory();

    int farMoreRentDay(String fieldName, int rentDaysMore);
}
