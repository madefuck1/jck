package com.soufang.Vo;

import com.soufang.base.Result;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
public class AdminVo {

    private String code ;
    private String msg ;
    private int count ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AdminVo() {
        this.code = "0";
    }

    public AdminVo(Result result){
        if(result.isSuccess()){
            this.code = "0";
        }else {
            this.code = "1";
        }
        this.msg = result.getMessage();
    }
}
