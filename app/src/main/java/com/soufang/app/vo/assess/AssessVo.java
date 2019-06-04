package com.soufang.app.vo.assess;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.assess.AssessDto;
import io.swagger.models.auth.In;

import java.util.List;

public class AssessVo extends AppVo {
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
