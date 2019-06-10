package com.soufang.base.search.product;

import com.soufang.base.PageBase;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductManageSo extends PageBase {
    // 店铺id
    private Long shopId;
    // 产品名称
    private String productName;
    // 所属分类id
    private Long assortId;
    // 是否上架
    private Integer isUpper;
    private String startDate;
    private String endDate;

}
