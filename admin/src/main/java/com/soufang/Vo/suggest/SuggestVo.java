package com.soufang.Vo.suggest;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.suggest.SuggestDto;

import java.util.List;

public class SuggestVo extends AdminVo {

    private List<SuggestDto> data;

    public List<SuggestDto> getData() {
        return data;
    }

    public void setData(List<SuggestDto> data) {
        this.data = data;
    }
}
