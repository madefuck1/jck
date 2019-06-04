package com.soufang.mapper;

import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.product.ProductSpecDto;
import com.soufang.model.ProductSpec;
import com.soufang.model.ShopCarProduct;

import java.math.BigDecimal;

import java.util.List;

public interface ProductSpecMapper {
    int deleteByPrimaryKey(Long productSpecId);

    int insert(ProductSpec record);

    int insertSelective(ProductSpec record);

    ProductSpec selectByPrimaryKey(Long productSpecId);

    int updateByPrimaryKeySelective(ProductSpec record);

    int updateByPrimaryKey(ProductSpec record);

    BigDecimal selectPrice(ShopCarProduct shopCarProduct);

    List<ProductSpecDto> getProductSpecList(ProductSpecDto dto);

}