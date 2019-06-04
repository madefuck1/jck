package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/4/27
 * @Description:
 */
public enum OrderTypeEnum {


    GUANG_JIAO_HUI(1,"广交会"),
    ON_LINE(2,"线上订单"),
    OFF_LINE(3,"线下订单");

    private Integer value ;
    private String message ;

    OrderTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

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

    public static Map<String,String> getAll(){
        Map<String,String> map = new HashMap<>();
        for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
            map.put(orderTypeEnum.getValue()+"",orderTypeEnum.getMessage());
        }
        return map ;
    }
}
