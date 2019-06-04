package com.soufang.vo.assess;

import com.soufang.base.dto.assess.AssessDto;
import com.soufang.vo.BaseVo;

import java.util.List;

public class AssessVo extends BaseVo {
    private List<AssessDto> data;
    private Integer count;
    private Integer page;
    private Integer type;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
