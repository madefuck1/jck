package com.soufang.app.vo.push;

import com.soufang.app.vo.AppVo;

import java.util.Map;

public class UnReadPushVo extends AppVo {
    private Map<String,Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
