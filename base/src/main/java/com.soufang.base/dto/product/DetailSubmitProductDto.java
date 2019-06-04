package com.soufang.base.dto.product;

import com.soufang.base.dto.address.AddressDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DetailSubmitProductDto {

    private Long productId;

    private String productName;

    private Long shopId;

    private String shopName;

    private String productSpecName;

    private String productImageUrl;

    private Long productNumber;

    private BigDecimal productPrice;

    private AddressDto addressDto;
}
