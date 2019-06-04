package com.soufang.base.dto.shopCar;

import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.product.ProductColorDto;
import com.soufang.base.dto.product.ProductSpecDto;
import com.soufang.base.dto.product.ProductStatisticsDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ShopCarProductDto {

    private Long shopCarProductId;

    private Long shopCarId;

    private Long productId;

    private BigDecimal productNumber;

    private String productUnit;

    private String productSpec;

    private String productColor;

    private String productName;

    private String productImage;

    private String productUrl;

    public String getProductUrl() {
        return PropertiesParam.file_pre + productImage;
    }

    private BigDecimal productPrice;
    private Long min;
    private Long max;
    private Long spock;

    private List<ProductSpecDto> productSpecDtoList;
    private ProductStatisticsDto productStatisticsDto;

    // 产品对应颜色对象
    private ProductColorDto productColorDto;

}
