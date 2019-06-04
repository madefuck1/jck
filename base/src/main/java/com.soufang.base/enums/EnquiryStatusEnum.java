package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;
/**
 * @Auther: xioasan
 * @Date: 2019/5/23
 * @Description:
 */

public enum EnquiryStatusEnum {
    to_auditing(1,"审核中"),
    audit_failed(2,"审核失败"),
    already_offer(3,"已报价"),
    purchasing_success(4,"采购成功"),
    del(0,"删除");


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

    EnquiryStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }
    public static Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        for (EnquiryStatusEnum enquiryStatusEnum : EnquiryStatusEnum.values()) {
            map.put(enquiryStatusEnum.getValue() + "", enquiryStatusEnum.getMessage());
        }
        return map;
    }

    public static EnquiryStatusEnum getByKey(Integer value){
        for (EnquiryStatusEnum enquiryStatusEnum : EnquiryStatusEnum.values()) {
            if(enquiryStatusEnum.value == value){
                return enquiryStatusEnum;
            }
        }
        return  null ;
    }

}
