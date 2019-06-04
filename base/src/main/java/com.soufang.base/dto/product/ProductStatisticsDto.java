package com.soufang.base.dto.product;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: chen
 * @Date: 2019/4/25
 * @Description:
 */
@Getter
@Setter
public class ProductStatisticsDto {

    private Long productStatisticsId;

    private Long productId;

    private Long collectionNumber;

    private Long browseNumber;

    private Long dealNumber;

    private String productPrice;

    private String productStock;

    // type{1：收藏；2：交易；3：浏览 }
    private int type;
}
