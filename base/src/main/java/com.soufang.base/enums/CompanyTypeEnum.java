package com.soufang.base.enums;

/**
 * @Auther: chen
 * @Date: 2019/6/4
 * @Description:
 */
public enum CompanyTypeEnum {

    person(1,"个体工商户"),
    company(2,"企业");


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

    CompanyTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }
}
