package com.soufang.service.impl;

import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import com.soufang.mapper.AssessMapper;
import com.soufang.service.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("assessService")
public class AssessServiceImpl implements AssessService {

    @Autowired
    AssessMapper assessMapper;

    @Override
    public PageHelp<AssessDto> getList(AssessSo assessSo) {
        assessSo.setPage((assessSo.getPage() - 1) * assessSo.getLimit());
        List<AssessDto> list = assessMapper.getList(assessSo);
        int count = assessMapper.getCount(assessSo);
        PageHelp<AssessDto> pageHelp = new PageHelp<>();
        pageHelp.setCount(count);
        pageHelp.setData(list);
        return pageHelp;
    }
}

