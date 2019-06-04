package com.soufang.Vo.news;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.news.NewsDto;

import java.util.List;

public class NewsVo extends AdminVo {
    private List<NewsDto> data;

    public List<NewsDto> getData() {
        return data;
    }

    public void setData(List<NewsDto> data) {
        this.data = data;
    }
}
