package com.soufang.service;

import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.search.assort.AssortSo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/4/20
 * @Description:
 */
public interface AssortService {

    int deleteByPrimaryKey(Long assortId);

    int insertSelective(AssortDto record);

    AssortDto selectByPrimaryKey(Long assortId);

    int updateByPrimaryKeySelective(AssortDto record);

    List<AssortDto> getAll();

    List<AssortDto> getList(AssortDto assort);

    /**
     * 询盘中商品类别的下拉列表
     * @return
     */
    List<Map<String, Object>> getIdName();

    Map<String,Object> getAssortByKey(AssortSo so);
}
