package com.soufang.base.enums;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
public enum ContractStatusEnum {
    to_upload(0,"待提交"),
    to_confirm(1,"待审核"),
    success(2,"审核通过"),
    fail(3,"审核失败");

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

    ContractStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }
}
