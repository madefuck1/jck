package com.soufang.mapper;

import com.soufang.base.dto.storeConstruction.StoreCurouselMapDto;
import com.soufang.model.StoreCurouselMap;

import java.util.List;

public interface StoreCurouselMapMapper {
    int deleteByPrimaryKey(Long storeCurouselMapId);

    int insert(StoreCurouselMap record);

    int insertSelective(StoreCurouselMap record);

    StoreCurouselMap selectByPrimaryKey(Long storeCurouselMapId);

    int updateByPrimaryKeySelective(StoreCurouselMap record);

    int updateByPrimaryKey(StoreCurouselMap record);

    int delAllChart(Long storeConstructionId);

    List<StoreCurouselMapDto> getMapList(Long storeConstructionId);
}