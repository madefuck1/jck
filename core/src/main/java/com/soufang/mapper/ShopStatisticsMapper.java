package com.soufang.mapper;

import com.soufang.base.dto.shop.ShopStatisticsDto;
import com.soufang.model.ShopStatistics;

public interface ShopStatisticsMapper {
    int deleteByPrimaryKey(Long shopStatisticsId);

    int insert(ShopStatistics record);

    int insertSelective(ShopStatistics record);

    ShopStatistics selectByPrimaryKey(Long shopStatisticsId);

    int updateByPrimaryKeySelective(ShopStatistics record);

    int updateByPrimaryKey(ShopStatistics record);

    ShopStatisticsDto getInfoByShopId(Long shopId);
}