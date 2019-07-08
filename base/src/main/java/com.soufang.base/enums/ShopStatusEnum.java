package com.soufang.base.enums;

/**
 * @Auther: chen
 * @Date: 2019/6/5
 * @Description:
 */
public enum ShopStatusEnum {

    success(0, "店铺审核已通过,请前往个人中心切换；"),
    ing(2, "店铺正在审核中,请耐心等待"),
    fail(3, "店铺审核失败,详情请咨询400-860-0992");

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
