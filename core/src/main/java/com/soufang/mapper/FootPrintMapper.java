package com.soufang.mapper;

import com.soufang.base.search.footPrint.FootPrintSo;
import com.soufang.model.FootPrint;

import java.util.List;

public interface FootPrintMapper {

    int deleteByPrimaryKey(long footprintId);

    int insert(FootPrint record);

    int insertSelective(FootPrint record);

    FootPrint selectByPrimaryKey(String footprintId);

    int updateByPrimaryKeySelective(FootPrint record);

    int updateByPrimaryKey(FootPrint record);

    //查询列表
    List<FootPrint> getFootPrintList(FootPrintSo footPrintSo);

    List<FootPrint> selFootPrintOneWeek(FootPrintSo footPrintSo);

    int getFoPtCount(FootPrint record);

}