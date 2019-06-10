package com.soufang.mapper;

import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import com.soufang.model.StoreConstruction;

public interface StoreConstructionMapper {
    int deleteByPrimaryKey(Long storeConstructionId);

    int insert(StoreConstruction record);

    int insertSelective(StoreConstruction record);

    StoreConstruction selectByPrimaryKey(Long storeConstructionId);

    int updateByPrimaryKeySelective(StoreConstruction record);

    int updateByPrimaryKey(StoreConstruction record);

    StoreConstructionDto getStoreCInfo(Long shopId);

    int publish(Long shopId);
}