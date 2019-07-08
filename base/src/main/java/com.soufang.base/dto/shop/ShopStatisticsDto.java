package com.soufang.base.dto.shop;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShopStatisticsDto {

    private Long shopStatisticsId;

    private Long shopId;

    private Long shopProductCount;

    private Long shopCollectionCount;

    private Long shopBrowseCount;

}
