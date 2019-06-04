package com.soufang.app.vo.productManage;

import com.soufang.base.PageBase;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ListProductReqVo extends PageBase {

    // 产品名称
    private String productName;
    // 分类id
    private Long assortId;
    // 价格排序  （0:正序;1:倒序）
//    private Integer priceSort;
    // 销量排序  （0:正序;1:倒序）
//    private Integer sellSort;
    //    // 起始价格
    //    private BigDecimal minPrice;
    //    // 结束价格
    //    private BigDecimal maxPrice;
    //    // 发货地
    //    private String placeOfDelivery;

    // 排序 （0:正序;1:倒序）
    private Integer sort;
    // 排序类型 (1:销量；2：浏览量；3：价格)
    private Integer sortType;
}
