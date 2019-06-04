package com.soufang.app.vo;

/**
 * @Auther: chen
 * @Date: 2019/4/30
 * @Description:
 */
public class AppVo {

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

    public AppVo() {
        this.success = true ;
    }
}
