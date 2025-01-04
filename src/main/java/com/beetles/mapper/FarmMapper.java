package com.beetles.mapper;

import com.beetles.PO.AnimalAddDTO;
import com.beetles.PO.FarmInfoByNameVO;
import com.beetles.PO.LoginDTO;
import com.beetles.PO.POJO.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FarmMapper {
    List<String> getFieldsName();

    int login(LoginDTO loginDTO);

    List<FarmInfoByNameVO> getFarmInfoByName(String field_name);

    int changeFieldName(String fieldName, String changedName);

    int selectFarmByName(String changedName);

    void setAnimalStatus(String fieldName, String animalName);

    int getTotalSize(String fieldName);

    List<SizeVO> getCurrentSize(String fieldName);

    void deleteAnimalById(int id);

    List<CheckLife> getLifeAndBirthTime();

    List<IncomeCheck> checkIncome();

    int selectFarmNameInAO(String fieldName);

    int changeFieldNameOnlyInF(String fieldName, String changedName);

    List<Animal> getAnimalInfo();

    float getMoney();

    int getSizeTotalAdd(String animalName);

    int animalAdd(AnimalAddDTO animalAddDTO);

    void setMoney(float income);

    float getBuyPrice(String animalName);

    void buyAnimal(float remainMoney);

    int fieldExpand(String fieldName, int size);

    int getFieldSize(String fieldName);

    void setUserCreateTime(LocalDateTime now);

    LocalDateTime getUserCreateTime();

    void setHistory(LocalDateTime createTime, float money);

    void deletAllAnimalOwnInfo();

    void deleteAllFieldInfo();

    void setDefaultField(String fieldName, LocalDateTime now, int size);

    void setDefaultfield(LocalDateTime now);

    List<History> checkHistory();

    void fieldBorrow(String fieldBorrowName, float fieldBorrowChoose, LocalDateTime now);

    List<FieldInfo> getFieldInfo();

    void deleteAnimalByFieldName(String fieldName);

    int checkAnimalByFieldName(String fieldName);

    void deleteFieldByName(String fieldName);

    LocalDateTime getFarmCreateTimeByName(String fieldName);

    int getFarmRentDayByName(String fieldName);

    void setRentDay(int rentDay, String fieldName);
}
