package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;

public enum  OauthTypeEnum {
    //微信
    weChat(1,"微信"),
    //QQ
    QQ(2,"QQ"),
    //微博
    web(3,"微博");

    private int value;
    private String message;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    OauthTypeEnum(int value, String message) {
        this.value = value;
        this.message = message;
    }
    public static Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        for (OauthTypeEnum oauthTypeEnum : OauthTypeEnum.values()) {
            map.put(oauthTypeEnum.getValue() + "", oauthTypeEnum.getMessage());
        }
        return map;
    }

    public static OauthTypeEnum getByKey(int value){
        for (OauthTypeEnum oauthTypeEnum : OauthTypeEnum.values()) {
            if(oauthTypeEnum.value == value){
                return oauthTypeEnum;
            }
        }
        return  null ;
    }
}
