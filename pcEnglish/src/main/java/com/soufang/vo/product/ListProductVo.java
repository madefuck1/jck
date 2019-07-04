package com.soufang.vo.product;

import com.soufang.base.dto.product.ProductDto;

import java.util.List;

public class ListProductVo {

    List<ProductDto> data;
    private Integer count;

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
