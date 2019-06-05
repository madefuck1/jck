package com.soufang.vo.assess;

import com.soufang.base.dto.assess.AssessDto;
import com.soufang.vo.BaseVo;

import java.util.List;

public class AssessVo extends BaseVo {
    private List<AssessDto> data;
    private Integer count;


    public List<AssessDto> getData() {
        return data;
    }

    public void setData(List<AssessDto> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}
