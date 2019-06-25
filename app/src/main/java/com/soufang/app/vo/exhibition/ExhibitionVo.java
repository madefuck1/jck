package com.soufang.app.vo.exhibition;

import com.soufang.base.dto.exhibition.ExhibitionDto;

import java.util.List;

public class ExhibitionVo {
    private List<ExhibitionDto> data;

    public List<ExhibitionDto> getData() {
        return data;
    }

    public void setData(List<ExhibitionDto> data) {
        this.data = data;
    }
}
