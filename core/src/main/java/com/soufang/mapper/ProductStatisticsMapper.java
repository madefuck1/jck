package com.soufang.mapper;

import com.soufang.model.ProductStatistics;

public interface ProductStatisticsMapper {
    int deleteByPrimaryKey(Long productStatisticsId);

    int insert(ProductStatistics record);

    int insertSelective(ProductStatistics record);

    ProductStatistics selectByPrimaryKey(Long productStatisticsId);

    int updateByPrimaryKeySelective(ProductStatistics record);

    int updateByPrimaryKey(ProductStatistics record);

    ProductStatistics selectByProductId(Long productId);
}