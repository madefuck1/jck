package com.soufang.service;

import com.soufang.base.dto.assort.AssortDto;

import java.util.List;
import java.util.Map;

public interface CommonPullDownService {

    List<Map<String,Object>> getShopIdAndNameOption();

    List<Map<String, Object>> getAssortIdAndName();

    List<AssortDto> getFirstAssort(Long parentId);

    List<AssortDto> getAssortByParentId(Long parentId);

    List<AssortDto> getAssortAByParentId(Long parentId);

    List<AssortDto> selUnderAssort(Long assortId);

}
