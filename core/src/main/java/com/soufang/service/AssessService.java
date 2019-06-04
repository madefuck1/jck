package com.soufang.service;

import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;



public interface AssessService {

    PageHelp<AssessDto> getList(AssessSo assessSo);

}
