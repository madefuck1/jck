package com.soufang.Vo.shop;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.shop.ShopDto;

import java.util.List;

public class ShopVo extends AdminVo {

    private List<ShopDto> data;

    public List<ShopDto> getData() {
        return data;
    }

    public void setData(List<ShopDto> data) {
        this.data = data;
    }
}
