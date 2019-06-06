package com.soufang.mapper;

import com.soufang.base.dto.storeConstruction.StoreExclusiveAssortDto;
import com.soufang.model.StoreExclusiveAssort;

import java.util.List;

public interface StoreExclusiveAssortMapper {
    int deleteByPrimaryKey(Long exclusiveAssortId);

    int insert(StoreExclusiveAssort record);

    int insertSelective(StoreExclusiveAssort record);

    StoreExclusiveAssort selectByPrimaryKey(Long exclusiveAssortId);

    int updateByPrimaryKeySelective(StoreExclusiveAssort record);

    int updateByPrimaryKey(StoreExclusiveAssort record);

    List<StoreExclusiveAssortDto> getStoreAssort(StoreExclusiveAssortDto dto);

    int updExclusiveAssort(StoreExclusiveAssortDto temp);
}