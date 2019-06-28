package com.soufang.base;

/**
 * @Auther: chen
 * @Date: 2019/4/10
 * @Description:
 */
public class Result {

    private boolean success ;
    private String message ;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public Result() {
        success = true;
    }
}
