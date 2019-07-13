package com.soufang.base.enums;

import java.util.HashMap;
import java.util.Map;

public enum ErrorEnum {
    //用户名错误
    username_error(100000,"username error"),
    //用户密码输入错误
    userPassword_error(100001,"User password error"),
    //注册时用户名已存在
    register_error(100002,"Username already exists"),
    //更新用户失败
    updateUserInfo_error(100003,"Failed to update user information"),
    //修改密码失败
    updatePassword_error(100004,"Password change failed"),
    //删除失败
    delete_error(100005,"failed to delete"),
    //删除成功
    delete_true(100006,"successfully deleted"),
    //编辑失败
    update_error(100006,"Edit failed"),
    //取消失败
    cancel_error(100007,"Cancel failure"),
    //操作成功
    success(100008,"Successful operation"),
    //操作失败
    failed(100009,"operation failed"),
    //添加反馈失败
    addSuggest_error(200001,"Add feedback failed"),
    //添加反馈成功
    addSuggest_true(200002,"Add feedback successfully"),
    //添加店铺失败
    addShop_error(300001,"Adding store failed"),
    //店铺不存在
    shop_error(300002,"The store does not exist"),
    //添加购物车失败
    addShopCar_error(300002,"fail to add in order cart"),
    //推送成功
    push_true(400001,"Push success"),
    //推送成功
    push_error(400002,"Push failed"),
    //修改价格成功
    updatePrice_true(500001,"Modified price successfully"),
    //修改价格失败
    updatePrice_error(500002,"Modified price failed"),
    //新增产品成功
    addProduct_true(600001,"New product success"),
    //新增产品失败
    addProduct_error(600002,"New product failed"),
    //店铺信息没有完善
    shopInfo_error(700001,"Shop information is not perfect"),
    //产品信息没有完善
    productInfo_error(700002,"Product information is not perfect"),
    //发货成功
    delivery_true(700003,"Successful delivery"),
    //发货失败
    delivery_error(700004,"Delivery failed"),
    //没有店铺信息
    noSHopInfo_error(700005,"No store information"),
    //填写信息错误
    inputInfo_error(700003,"Fill in the wrong information"),
    //订单生成失败
    createOrder_error(800001,"Order generation failed"),
    //订单生成成功
    createOrder_true(800002,"Order generated successfully"),
    //订单支付失败
    orderPay_error(800003,"Order payment failed"),
    //修改订单失败
    updateOrder_error(800006,"修改订单失败"),
    //卖家确认订单报错
    orderInfo_error(800004,"Order information error"),
    //订单状态不对
    orderStatus_error(800005,"The order status is incorrect"),
    //确认合同报错
    contract_error(900001,"Confirm contract error"),
    //支付成功
    pay_true(110001,"payment successful"),
    //支付失败
    pay_error(110002,"Payment failed"),
    //修改询盘失败
    updateEnquiry_error(120001,"Modify inquiry failed"),
    //添加询盘产品失败
    addEnquiryProduct_error(120002,"Adding an inquiry product failed"),
    //修改询盘产品失败
    updateEnquiryProduct_error(120003,"Modifying the inquiry product failed"),
    //添加公司报错
    addCompany_error(130001,"Add company error"),
    //图片上传失败
    uploadPicture_error(130002,"Image upload failed");


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
    ErrorEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public static Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        for (ErrorEnum errorEnum : ErrorEnum.values()) {
            map.put(errorEnum.getValue() + "", errorEnum.getMessage());
        }
        return map;
    }

    public static ErrorEnum getByKey(Integer value){
        for (ErrorEnum errorEnum : ErrorEnum.values()) {
            if(errorEnum.value == value){
                return errorEnum;
            }
        }
        return  null ;
    }
}
