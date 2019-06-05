package com.soufang.service;

import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;

import java.util.Map;


public interface AssessService {

    PageHelp<AssessDto> getList(AssessSo assessSo);

    Map<String,Integer> getCount(AssessSo assessSo);
}
