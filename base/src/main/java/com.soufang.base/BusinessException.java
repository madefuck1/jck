package com.soufang.base;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
public class BusinessException extends RuntimeException {

    private String code ;
    private String message ;

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
