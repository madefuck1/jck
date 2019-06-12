package com.soufang.base.dto.storeConstruction;

import com.soufang.base.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class StoreProductAssortDto {
    private Long storeProductAssortId;

    private Long exclusiveAssortId;
    private String assortName;

    private Long shopId;

    private Long productId;

    private Integer isShow;

    private ProductDto productDto;

    // 检索条件
    // 关键词
    private String productName;

    // 具体分类id
    private List<String> exclusiveAssortIds;

}