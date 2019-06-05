package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: xioasan
 * @Date: 2019/5/23
 * @Description:
 */

public enum PurchaseStatusEnum {

    //报价
    already_offer(1,"已报价"),
    //采用报价
    success(2,"接收"),
    //交易完成
    refund(3,"拒绝");


    private Integer value;
    private String message;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    PurchaseStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }
    public static Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        for (PurchaseStatusEnum enquiryStatusEnum : PurchaseStatusEnum.values()) {
            map.put(enquiryStatusEnum.getValue() + "", enquiryStatusEnum.getMessage());
        }
        return map;
    }

    public static PurchaseStatusEnum getByKey(Integer value){
        for (PurchaseStatusEnum enquiryStatusEnum : PurchaseStatusEnum.values()) {
            if(enquiryStatusEnum.value == value){
                return enquiryStatusEnum;
            }
        }
        return  null ;
    }

}
