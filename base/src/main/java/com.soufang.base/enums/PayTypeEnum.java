package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/5/22
 * @Description:
 */
public enum PayTypeEnum {

    zhifubao(1,"支付宝"),
    wechat(2,"微信");

    private Integer value ;
    private String message ;

    PayTypeEnum(Integer value, String message) {
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
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            map.put(payTypeEnum.getValue()+"",payTypeEnum.getMessage());
        }
        return map ;
    }
}
