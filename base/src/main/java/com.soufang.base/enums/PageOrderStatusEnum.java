package com.soufang.base.enums;

/**
 * @Auther: chen
 * @Date: 2019/5/23
 * @Description:
 */
public enum  PageOrderStatusEnum {


    review(1, "审核中"),
    to_pay(2, "待支付"),
    to_send(3, "待发货"),
    to_take(4, "待收货"),
    all(0,"全部");

    private Integer value;
    private String message;


    public static String getOrderStatusList(Integer value){
        switch (value){
            case 1 :
                return "0,1";
            case 2:
                return "3,5";
            case 3 :
                return "6";
            case 4 :
                return "7";
            case 0 :
                return null ;
        }
        return null ;
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

    PageOrderStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }
}
