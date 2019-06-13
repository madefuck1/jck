package com.soufang.mapper;

import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.search.assort.AssortSo;
import com.soufang.model.Assort;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface AssortMapper {
    int deleteByPrimaryKey(Long assortId);

    int insertSelective(Assort record);

    Assort selectByPrimaryKey(Long assortId);

    int updateByPrimaryKeySelective(Assort record);

    List<Assort> getAll();

    List<Assort> getList(Assort assort);

    List<Map<String, Object>> getAssortIdAndName();

    /**
     * 询盘中商品类别的下拉列表
     * @return
     */
    List<Map<String, Object>> getIdName();


    List<AssortDto> getAssortByParentId(Long parentId);

    List<AssortDto> getAssortAByParentId(Long parentId);

    Map<String,Object> getAssortByKey(AssortSo so);

    Long getAssortIdByName(String assortName);

    Long selParentIdByAssortId(Long parentId);

    List<AssortDto> selUnderAssort(Long assordId);
}