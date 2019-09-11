package com.soufang.mapper;

import com.soufang.model.StoreView;

public interface StoreViewMapper {
    int deleteByPrimaryKey(Long storeViewId);

    int insert(StoreView record);

    int insertSelective(StoreView record);

    StoreView selectByPrimaryKey(Long storeViewId);

    int updateByPrimaryKeySelective(StoreView record);

    int updateByPrimaryKey(StoreView record);
}