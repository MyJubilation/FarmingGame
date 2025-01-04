package com.beetles.service.Impl;

import com.beetles.PO.AnimalAddDTO;
import com.beetles.PO.FarmInfoByNameVO;
import com.beetles.PO.LoginDTO;
import com.beetles.PO.POJO.*;
import com.beetles.mapper.FarmMapper;
import com.beetles.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {

    @Autowired
    private FarmMapper farmMapper;

    @Override
    public int login(LoginDTO loginDTO) {
        LocalDateTime now = LocalDateTime.now();
        farmMapper.setUserCreateTime(now);
        farmMapper.setDefaultfield(now);
        return farmMapper.login(loginDTO);
    }

    @Override
    public List<String> getFieldsName() {
        return farmMapper.getFieldsName();
    }

    @Override
    public List<FarmInfoByNameVO> getFarmInfoByName(String field_name) {
        return farmMapper.getFarmInfoByName(field_name);
    }

    @Override
    public int selectFarmByName(String changedName) {
        return farmMapper.selectFarmByName(changedName);
    }

    @Override
    public int changeFieldName(String fieldName, String changedName) {
        int result = farmMapper.selectFarmNameInAO(fieldName);
        if(result == 0){
            return farmMapper.changeFieldNameOnlyInF(fieldName, changedName);
        }else{
            return farmMapper.changeFieldName(fieldName,changedName);
        }
    }

    @Override
    public int getLastLifeByBirthTime(LocalDateTime birthTime, int life, int id) {
        Duration duration = Duration.between(birthTime, LocalDateTime.now());
        int seconds = (int) duration.getSeconds();
        if (seconds < life) {
            return life-seconds;
        }else {
//            farmMapper.setAnimalStatus(fieldName,animalName);
            farmMapper.deleteAnimalById(id);
            return 0;
        }
    }

    @Override
    public int getFarmRemainDayByName(String fieldName) {
        LocalDateTime time = farmMapper.getFarmCreateTimeByName(fieldName);
        int rentDay = farmMapper.getFarmRentDayByName(fieldName);
        Duration duration = Duration.between(time, LocalDateTime.now());
        int seconds = (int) duration.getSeconds();
        return rentDay-seconds;
    }

    @Override
    public SizeInfo getSize(String fieldName) {
        // 获取当前Size
        int currentSize = 0;
        List<SizeVO> currentSizeList = farmMapper.getCurrentSize(fieldName);
        for(SizeVO index : currentSizeList){
            currentSize += index.getSize()* index.getAnimalCount();
        }
        // 获取总Size
        int totalSize = farmMapper.getTotalSize(fieldName);
        SizeInfo sizeInfo = new SizeInfo();
        sizeInfo.setCurrentSize(currentSize);
        sizeInfo.setTotalSize(totalSize);
        return sizeInfo;
    }

    @Override
    public int checkLife() {
        int cnt = 0;
        // 查询所有的寿命和birthTime
        List<CheckLife> list = farmMapper.getLifeAndBirthTime();
        // 增强for比较
        for(CheckLife life : list){
            Duration duration = Duration.between(life.getBirthTime(), LocalDateTime.now());
            int seconds = (int) duration.getSeconds();
            if (seconds > life.getLife()) {
                farmMapper.deleteAnimalById(life.getId());
                cnt++;
            }
        }
        // 如果有则删除
        return cnt;
    }

    @Override
    public float checkIncome() {
        // 获得每日收益的List，用for做加法
        float income = farmMapper.getMoney();
        List<IncomeCheck> list = farmMapper.checkIncome();
        for(IncomeCheck f : list){
            income += f.getIncome()*f.getAnimalCount();
        }
        // 存储进数据库
        farmMapper.setMoney(income);
        return income;
    }

    @Override
    public List<Animal> getAnimalInfo() {
        return farmMapper.getAnimalInfo();
    }

    @Override
    public int getSizeTotalAdd(String animalName, int count) {
        int size = farmMapper.getSizeTotalAdd(animalName);
        return size * count;
    }

    @Override
    public int animalAdd(AnimalAddDTO animalAddDTO) {
        animalAddDTO.setAnimalStatus(1);
        animalAddDTO.setBirthTime(LocalDateTime.now());
        return farmMapper.animalAdd(animalAddDTO);
    }

    @Override
    public int checkMoney(AnimalAddDTO animalAddDTO) {
        float buyPrice = farmMapper.getBuyPrice(animalAddDTO.getAnimalName());
        float totalCost = buyPrice * animalAddDTO.getCount();
        float money = farmMapper.getMoney();
        if(money > totalCost){
            // 可以购买，返回1
            float remainMoney = money - totalCost;
            farmMapper.buyAnimal(remainMoney);
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int fieldExpand(FieldExpand fieldExpand) {
        float money = farmMapper.getMoney();
        if(money > fieldExpand.getFieldSize()){
            int size = farmMapper.getFieldSize(fieldExpand.getFieldName()) + fieldExpand.getFieldSize();
            int result = farmMapper.fieldExpand(fieldExpand.getFieldName(),size);
            if(result != 0){
                farmMapper.setMoney(money-fieldExpand.getFieldSize());
                return 1;
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    @Override
    public int checkTimeOut() {
        // 获取创建时间
        LocalDateTime createTime = farmMapper.getUserCreateTime();
        // 比较获取现在与创建时间间隔秒数
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createTime, now);
        int seconds = (int) duration.getSeconds();
        int GAME_TIME_SECOND = 60*60*2;
        if(seconds < GAME_TIME_SECOND){ // 秒*分*时  游戏时间
            return GAME_TIME_SECOND - seconds;
        }else{
            // 时间结束，生成历史记录，并清空animal_own表数据与field的除第一条外的数据
            // 生成历史记录
            float money = farmMapper.getMoney();
            farmMapper.setHistory(createTime, money);
            // 清空animal_own表数据与field的除第一条外的数据
            farmMapper.deletAllAnimalOwnInfo();
            farmMapper.deleteAllFieldInfo();
            farmMapper.setDefaultField("这是你的第一块土地",LocalDateTime.now(),400);
            farmMapper.setMoney(10000);
            return 0;
        }
    }

    @Override
    public void setHistory() {
        // 获取创建时间
        LocalDateTime createTime = farmMapper.getUserCreateTime();
        float money = farmMapper.getMoney();
        farmMapper.setHistory(createTime, money);
        // 清空animal_own表数据与field的除第一条外的数据
        farmMapper.deletAllAnimalOwnInfo();
        farmMapper.deleteAllFieldInfo();
        farmMapper.setDefaultField("这是你的第一块土地",LocalDateTime.now(),400);
        farmMapper.setMoney(10000);
    }

    @Override
    public void setDefaultfield() {
        farmMapper.setDefaultfield(LocalDateTime.now());
    }

    @Override
    public List<History> checkHistory() {
        return farmMapper.checkHistory();
    }

    @Override
    public int fieldBorrow(FieldBrrow fieldBrrow) {
        // 检查钱是否够
        float money = farmMapper.getMoney();
        if(money > fieldBrrow.getFieldBorrowChoose()){
            // 钱足够
            money -= fieldBrrow.getFieldBorrowChoose();
            farmMapper.setMoney(money);
            // 新添加农田
            farmMapper.fieldBorrow(fieldBrrow.getFieldBorrowName(),fieldBrrow.getFieldBorrowChoose(),LocalDateTime.now());
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int checkField() {
        int cnt = 0;
        // 检查是否到期
        List<FieldInfo> fieldInfoList = farmMapper.getFieldInfo();
        for(FieldInfo f : fieldInfoList){
            if(f.getId() != 1){
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(f.getCreateTime(), now);
                int seconds = (int) duration.getSeconds();
                if(seconds > f.getRentDay()){
                    // 超过租期，检查是否有animal_own有该农田动物
                    int result = farmMapper.checkAnimalByFieldName(f.getFieldName());
                    if(result != 0){
                        // 删除动物
                        farmMapper.deleteAnimalByFieldName(f.getFieldName());
                    }
                    // 删除农田
                    farmMapper.deleteFieldByName(f.getFieldName());
                    cnt++;
                }
            }
        }
        return cnt;
    }

    @Override
    public int farMoreRentDay(String fieldName, int rentDaysMore) {
        // 获取现在的租期，获取用户金额
        int rentDay = farmMapper.getFarmRentDayByName(fieldName);
        float money = farmMapper.getMoney();
        if(money > rentDaysMore){
            money -= rentDaysMore;
            farmMapper.setMoney(money);
            farmMapper.setRentDay((rentDay+rentDaysMore),fieldName);
            return 1;
        }else {
            return 0;
        }
    }
}
