package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/4/27
 * @Description:
 */
public enum OrderStatusEnum {

    to_submit(1, "未提交合同","blue"),
    review(2, "审核中","green"),
    to_pay(3, "定金待付","blue"),
    product(4,"商品准备中","secondary"),
    to_pay_last(5, "尾款待付","secondary"),
    to_send(6, "待发货","blue"),
    to_take(7, "待收货","blue"),
    access(8,"待评价","blue"),
    success(9, "已完成","blue"),
    fail(10, "审核失败","blue"),
    cancel(0,"订单已取消","blue"),
    del(-1, "删除","blue");

    private Integer value;
    private String message;
    private String buttonColor ;

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

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }

    OrderStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    OrderStatusEnum(Integer value, String message, String buttonColor) {
        this.value = value;
        this.message = message;
        this.buttonColor = buttonColor;
    }

    public static Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            map.put(orderStatusEnum.getValue() + "", orderStatusEnum.getMessage());
        }
        return map;
    }

    public static OrderStatusEnum getByKey(Integer value){
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
           if(orderStatusEnum.value == value){
               return orderStatusEnum;
           }
        }
        return  null ;
    }

    public static String getMessageByKey(Integer value){
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if(orderStatusEnum.value == value){
                return orderStatusEnum.getMessage();
            }
        }
        return  null ;
    }

}
