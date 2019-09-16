package com.soufang.mapper;

import com.soufang.base.dto.storeConstruction.StoreViewDto;
import com.soufang.model.StoreView;

import java.util.List;

public interface StoreViewMapper {
    int deleteByPrimaryKey(Long storeViewId);

    int insert(StoreView record);

    int insertSelective(StoreView record);

    StoreView selectByPrimaryKey(Long storeViewId);

    int updateByPrimaryKeySelective(StoreView record);

    int updateByPrimaryKey(StoreView record);

    List<StoreViewDto> getStoreViews(Long shopId);
}