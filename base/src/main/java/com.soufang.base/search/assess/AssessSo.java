package com.soufang.base.search.assess;

import com.soufang.base.PageBase;

public class AssessSo extends PageBase {
    private Long shopId;
    private Integer type;//评价的类型

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
