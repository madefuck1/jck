package com.soufang.app.vo.user;

import com.soufang.app.vo.AppVo;

public class UserVo extends AppVo {
    private String code;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

