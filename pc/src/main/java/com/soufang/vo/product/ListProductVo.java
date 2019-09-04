package com.soufang.vo.product;

import com.soufang.base.dto.banner.BannerDto;
import com.soufang.base.dto.product.ProductDto;

import java.util.List;

public class ListProductVo {

    List<ProductDto> data;
    private Integer count;
    List<BannerDto> assortpictures;

    public List<BannerDto> getAssortpictures() {
        return assortpictures;
    }

    public void setAssortpictures(List<BannerDto> assortpictures) {
        this.assortpictures = assortpictures;
    }

    public List<ProductDto> getData() {
        return data;
    }

    public void setData(List<ProductDto> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
