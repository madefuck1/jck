package com.soufang.app.vo.push;

import com.soufang.base.dto.push.PushDto;

import java.util.List;

public class PushVo {
    private List<PushDto> data;
    private Integer count;

    public List<PushDto> getData() {
        return data;
    }

    public void setData(List<PushDto> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
