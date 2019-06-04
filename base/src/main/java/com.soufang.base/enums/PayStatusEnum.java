package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/5/22
 * @Description:
 */
public enum PayStatusEnum {

    to_pay(1, "待支付"),
    success(2, "支付成功"),
    fail(3, "支付失败"),
    cancel(4,"取消支付");

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

    PayStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public static Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
            map.put(payStatusEnum.getValue() + "", payStatusEnum.getMessage());
        }
        return map;
    }

    public static PayStatusEnum getByKey(Integer value){
        for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
            if(payStatusEnum.value == value){
                return payStatusEnum;
            }
        }
        return  null ;
    }
}
