package com.soufang.app.vo.user;

import com.soufang.app.vo.AppVo;

import java.util.Map;

public class UserVo extends AppVo {
    private String code;
    private Map<String,Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

