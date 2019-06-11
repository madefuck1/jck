package com.soufang.service;

import com.soufang.base.dto.exhibition.ExhibitionDto;
import com.soufang.base.search.exhibition.ExhibitionSo;

import java.util.List;

public interface ExhibitionService {

    //查询所有
    List<ExhibitionDto>  selExhibition(ExhibitionSo exhibitionSo);

}
