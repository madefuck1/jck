package com.soufang.mapper;

import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.storeConstruction.StoreProductAssortDto;
import com.soufang.model.StoreProductAssort;

import java.util.List;

public interface StoreProductAssortMapper {
    int deleteByPrimaryKey(Long storeProductAssortId);

    int insert(StoreProductAssort record);

    int insertSelective(StoreProductAssort record);

    StoreProductAssort selectByPrimaryKey(Long storeProductAssortId);

    int updateByPrimaryKeySelective(StoreProductAssort record);

    int updateByPrimaryKey(StoreProductAssort record);

    List<StoreProductAssortDto> searchProduct(StoreProductAssortDto storeProductAssortDto);

    List<ProductDto> initProduct(ProductDto productDto);

    // 店铺产品数量
    int initProductCount(ProductDto productDto);

    int delProductAssort(StoreProductAssortDto productAssortDto);
}