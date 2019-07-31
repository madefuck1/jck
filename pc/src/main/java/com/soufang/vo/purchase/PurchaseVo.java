package com.soufang.vo.purchase;

import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.vo.BaseVo;

import java.util.List;

public class PurchaseVo extends BaseVo {
    private List<PurchaseDto> data;
    private Integer count;

    public List<PurchaseDto> getData() {
        return data;
    }

    public void setData(List<PurchaseDto> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
