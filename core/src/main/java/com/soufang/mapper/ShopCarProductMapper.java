package com.soufang.mapper;

import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.model.ShopCarProduct;

public interface ShopCarProductMapper {
    int deleteByPrimaryKey(Long shopCarProductId);

    int insert(ShopCarProduct record);

    int insertSelective(ShopCarProduct record);

    ShopCarProduct selectByPrimaryKey(Long shopCarProductId);

    int updateByPrimaryKeySelective(ShopCarProduct record);

    int updateByPrimaryKey(ShopCarProduct record);

    ShopCarProduct isExistProduct(ShopCarProductDto temp);
}