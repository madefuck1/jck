package com.soufang.base.dto.shopCar;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ShopCarDto {

    private Long shopCarId;

    private Long userId;

    private Long shopId;
    private String shopName;


    private Date createTime;

    private List<ShopCarProductDto> shopCarProductDtoList;

    private ShopCarProductDto shopCarProductDto;


    // 购物车产品id数组集合
    private List<Long> shopCarProductIds;
}

