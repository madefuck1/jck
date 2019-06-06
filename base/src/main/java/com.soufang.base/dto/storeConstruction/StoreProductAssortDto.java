package com.soufang.base.dto.storeConstruction;

import com.soufang.base.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StoreProductAssortDto {
    private Long storeProductAssortId;

    private Long exclusiveAssortId;

    private Long shopId;

    private Long productId;

    private Integer isShow;

    private ProductDto productDto;

    // 检索条件
    // 关键词
    private String productName;

}