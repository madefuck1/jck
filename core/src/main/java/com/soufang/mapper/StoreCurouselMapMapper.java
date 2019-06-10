package com.soufang.mapper;

import com.soufang.model.StoreCurouselMap;

public interface StoreCurouselMapMapper {
    int deleteByPrimaryKey(Long storeCurouselMapId);

    int insert(StoreCurouselMap record);

    int insertSelective(StoreCurouselMap record);

    StoreCurouselMap selectByPrimaryKey(Long storeCurouselMapId);

    int updateByPrimaryKeySelective(StoreCurouselMap record);

    int updateByPrimaryKey(StoreCurouselMap record);

    int delAllChart(Long storeConstructionId);
}