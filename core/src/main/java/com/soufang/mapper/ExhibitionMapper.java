package com.soufang.mapper;

import com.soufang.base.search.exhibition.ExhibitionSo;
import com.soufang.model.Exhibition;

import java.util.List;

public interface ExhibitionMapper {
    //查询列表
    List<Exhibition> selExhibition(ExhibitionSo exhibitionSo);
}
