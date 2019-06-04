package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserLevelEnum {
    one(1, "普通会员"),
    two(2, "青铜会员"),
    three(3, "白银会员"),
    four(4,"钻石会员");

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

    UserLevelEnum(Integer value, String message) {
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

    public static String getByKey(Integer value){
        for (UserLevelEnum userLevelEnum : UserLevelEnum.values()) {
            if(userLevelEnum.value == value){
                return userLevelEnum.getMessage();
            }
        }
        return  null ;
    }

}
