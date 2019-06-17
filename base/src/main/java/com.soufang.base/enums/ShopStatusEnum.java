package com.soufang.base.enums;

/**
 * @Auther: chen
 * @Date: 2019/6/5
 * @Description:
 */
public enum ShopStatusEnum {

    success(0, "审核通过"),
    ing(2, "审核中"),
    fail(3, "审核失败");

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

    ShopStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public static  String getShopStatusByKey(int value){
        for (ShopStatusEnum shopStatusEnum : ShopStatusEnum.values()) {
            if(shopStatusEnum.value == value){
                return shopStatusEnum.getMessage();
            }
        }
        return  null ;
    }

}
