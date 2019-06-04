package com.soufang.service;

import com.soufang.base.dto.footprint.FootPrintDto;
import com.soufang.base.search.footPrint.FootPrintSo;

import java.util.List;

public interface FootPrintService {

    // 新增足迹
    boolean insert(FootPrintDto dto);

    // 查询足迹
    List<FootPrintDto> getFootPrintList( FootPrintSo footPrintSo);

    List<FootPrintDto>  selFootPrintOneWeek( FootPrintSo footPrintSo);

    int getFoPtCount( FootPrintSo footPrintSo);

    int delFootPrintById(long footPrintId);

    FootPrintDto selFootPrintById( String footPringtId);

}
