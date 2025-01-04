package com.beetles.controller;

import com.alibaba.fastjson.JSONObject;
import com.beetles.PO.AnimalAddDTO;
import com.beetles.PO.FarmInfoByNameVO;
import com.beetles.PO.LoginDTO;
import com.beetles.PO.POJO.*;
import com.beetles.Result.Result;
import com.beetles.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class FarmController {

    @Autowired
    private FarmService farmService;

    /**
     * 用户登录验证
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO loginDTO){
        int result = farmService.login(loginDTO);
        if(result == 0){
            return new Result<>().error("账号或密码错误");
        }else {
            return new Result<>().success(200,"登录成功");
        }
    }

    @GetMapping("/getFieldsName")
    public Result<?> getFieldsName() {
        List<String> nameList = farmService.getFieldsName();
        return new Result<>().success().put(nameList);
    }

    /**
     * 根据名字获取农田信息
     */
    @PostMapping("/getFarmInfoByName")
    public Result<?> getFarmInfoByName(@RequestBody Map<String, Object> fieldMap){
        String field_name = fieldMap.get("field_name").toString();
        List<FarmInfoByNameVO> vo = farmService.getFarmInfoByName(field_name);
        // TODO 将每个存储在新的返回List中
        List<FarmInfoList> result = new ArrayList<>();
        for(FarmInfoByNameVO vo1 : vo){
            // 名称，图片，剩余寿命，每天收益
            String animalName = vo1.getAnimalName();
            String imgUrl = vo1.getImgUrl();
            int lastLife = farmService.getLastLifeByBirthTime(vo1.getBirthTime(),vo1.getLife(),vo1.getId());
            float income = vo1.getIncome() * vo1.getAnimalCount();
            Duration duration = Duration.between(vo1.getCreateTime(), LocalDateTime.now());
            int seconds = (int) duration.getSeconds();// 创建时距今时间

            FarmInfoList farmInfoList = new FarmInfoList();
            farmInfoList.setAnimalName(animalName);
            farmInfoList.setImgUrl(imgUrl);
            farmInfoList.setLastLife(lastLife);
            farmInfoList.setIncome(income);

            result.add(farmInfoList);
        }
        if(result != null){
            return new Result<>().success().put(result);
        }else {
            return new Result<>().error("获取信息失败");
        }
    }
    /**
     * 获取当前农田剩余时间
     */
    @PostMapping("/getRemainDay")
    public Result<?> getRemainDay(@RequestBody Map<String, Object> fieldMap){
        String field_name = fieldMap.get("field_name").toString();
        int remainDay = farmService.getFarmRemainDayByName(field_name);
        return new Result<>().success().put(remainDay);
    }
    /**
     * 提前结束
     */
    @GetMapping("/setHistory")
    public Result<?> setHistory(){
        farmService.setHistory();
        return new Result<>().success();
    }

    /**
     * 修改当前农田名称
     */
    @PostMapping("/changeFieldName")
    public Result<?> changeFieldName(@RequestBody Map<String, Object> fieldMap){
        String fieldName = fieldMap.get("fieldName").toString();
        String changedName = fieldMap.get("changedName").toString();
        // 先查询有没有当前名字的农田
        int is_farm = farmService.selectFarmByName(changedName);
        if(is_farm == 0){
            int result = farmService.changeFieldName(fieldName,changedName);
            if(result != 0){
                return new Result<>().success("修改成功");
            }else {
                return new Result<>().error("修改失败");
            }
        }else {
            return new Result<>().error("名字已经存在，请修改！");
        }
    }
    /**
     * 获取当前农田大小以及已用大小
     */
    @PostMapping("/getSize")
    public Result<?> getSize(@RequestBody Map<String, Object> fieldMap){
        String field_name = fieldMap.get("field_name").toString();
        SizeInfo sizeInfo = farmService.getSize(field_name);
        if(sizeInfo != null){
            return new Result<>().success().put(sizeInfo);
        }else {
            return new Result<>().error("查询失败");
        }
    }
    /**
     * 查询是否有动物寿命为0，如果有则删除
     */
    @GetMapping("/checkLife")
    public Result<?> checkLife(){
        int result = farmService.checkLife();
        return new Result<>().success().put(result);
    }
    /**
     * 检查每日收入
     */
    @GetMapping("/checkIncome")
    public Result<?> checkIncome(){
        float income = farmService.checkIncome();
        return new Result<>().success().put(income);
    }
    /**
     * 获取动物商品信息
     */
    @GetMapping("/store/animalInfo")
    public Result<?> getAnimalInfo(){
        List<Animal> animalList = farmService.getAnimalInfo();
        if(animalList != null){
            return new Result<>().success().put(animalList);
        }else {
            return new Result<>().error("查询失败");
        }
    }
    /**
     * 增加动物到农田
     */
    @PostMapping("/animalAdd")
    public Result<?> animalAdd(@RequestBody AnimalAddDTO animalAddDTO){
        // 查询钱是否够用
        int moneyResrult = farmService.checkMoney(animalAddDTO);
        if(moneyResrult == 1){
            // 够用
            // 查询总size是否够用
            // 获取当前农田size信息
            SizeInfo sizeInfo = farmService.getSize(animalAddDTO.getFieldName());
            // 获取添加的size和
            int sizeTotalAdd = farmService.getSizeTotalAdd(animalAddDTO.getAnimalName(),animalAddDTO.getCount());
            if((sizeTotalAdd + sizeInfo.getCurrentSize()) <= sizeInfo.getTotalSize()){
                // 执行添加
                int result = farmService.animalAdd(animalAddDTO);
                if(result != 0){
                    return new Result<>().success("购买成功").put(result);
                }else {
                    return new Result<>().error("添加失败");
                }
            }else {
                return new Result<>().error("当前农田容量不足，请添加农田容量或更换农田！");
            }
        }else {
            return new Result<>().error("资金不足！");
        }
    }
    /**
     * 农田扩张
     */
    @PostMapping("/fieldExpand")
    public Result<?> fieldExpand(@RequestBody FieldExpand fieldExpand){
        int result = farmService.fieldExpand(fieldExpand);
        if(result != 0){
            return new Result<>().success().put(result);
        }else {
            return new Result<>().error("扩张失败");
        }
    }
    /**
     * 检查是否游戏时间结束
     */
    @GetMapping("/checkTimeOut")
    public Result<?> checkTimeOut(){
        int result = farmService.checkTimeOut();
        return new Result<>().success().put(result);
    }
    /**
     * 设置默认农田创建时间
     */
    @GetMapping("/setDefaultfield")
    public Result<?> setDefaultfield(){
        farmService.setDefaultfield();
        return new Result<>().success();
    }
    /**
     * 获取历史记录
     */
    @GetMapping("/checkHistory")
    public Result<?> checkHistory(){
        List<History> list = farmService.checkHistory();
        if(list != null){
            return new Result<>().success().put(list);
        }else {
            return new Result<>().error("历史记录为空");
        }
    }
    /**
     * 农田租用
     */
    @PostMapping("/fieldBorrow")
    public Result<?> fieldBorrow(@RequestBody FieldBrrow fieldBrrow){
        int result = farmService.fieldBorrow(fieldBrrow);
        if(result != 0){
            return new Result<>().success().put(result);
        }else {
            return  new Result<>().error("租用失败");
        }
    }
    /**
     * 检查农田剩余时间
     */
    @GetMapping("/checkField")
    public Result<?> checkField(){
        int result = farmService.checkField();
        if(result == 0){
            return new Result<>().success();
        }else {
            return new Result<>().error().put(result);
        }
    }
    /**
     * 添加指定农田租期
     */
    @PostMapping("/farMoreRentDay")
    public Result<?> farMoreRentDay(@RequestBody Map<String, Object> fieldMap){
        String field_name = fieldMap.get("fieldName").toString();
        int rentDaysMore = Integer.parseInt(fieldMap.get("rentDay").toString());
        int result = farmService.farMoreRentDay(field_name,rentDaysMore);
        if(result == 1){
            return new Result<>().success("延长成功，请重新刷新页面！");
        }else {
            return new Result<>().error("请检查金额或其他错误");
        }
    }
}
