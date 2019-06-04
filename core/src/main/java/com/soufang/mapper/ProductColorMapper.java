package com.soufang.mapper;

import com.soufang.base.dto.product.ProductColorDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.model.ProductColor;

public interface ProductColorMapper {
    int deleteByPrimaryKey(Long productColorId);

    int insert(ProductColor record);

    int insertSelective(ProductColor record);

    ProductColor selectByPrimaryKey(Long productColorId);

    int updateByPrimaryKeySelective(ProductColor record);

    int updateByPrimaryKey(ProductColor record);

    ProductColorDto getDetail(ShopCarProductDto temp2);
}