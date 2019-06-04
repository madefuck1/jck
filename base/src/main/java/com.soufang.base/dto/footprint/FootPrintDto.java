package com.soufang.base.dto.footprint;

import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.product.ProductStatisticsDto;
import com.soufang.base.dto.shop.ShopDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class FootPrintDto {
    private Long footprintId;

    private Long userId;

    private Long productId;

    private Long shopId;

    private Date createTime;

    private Integer page ;

    private Integer limit ;

    private ProductDto productDto;
    private ShopDto shopDto;
    private ProductStatisticsDto productStatisticsDto;

}
