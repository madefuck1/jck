package com.soufang.app.vo.banner;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.banner.BannerDto;

import java.util.List;

public class BannerVo extends AppVo {
    private List<BannerDto> data;


    public List<BannerDto> getData() {
        return data;
    }

    public void setData(List<BannerDto> data) {
        this.data = data;
    }
}
