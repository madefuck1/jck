package com.soufang.service.impl;

import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import com.soufang.mapper.AssessMapper;
import com.soufang.service.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("assessService")
public class AssessServiceImpl implements AssessService {

    @Autowired
    AssessMapper assessMapper;

    @Override
    public PageHelp<AssessDto> getList(AssessSo assessSo) {
        assessSo.setPage((assessSo.getPage() - 1) * assessSo.getLimit());
        List<AssessDto> list = assessMapper.getList(assessSo);
        for (AssessDto a :list) {
            a.setUserAvatar(PropertiesParam.file_pre+a.getUserAvatar());
        }
        int count = assessMapper.getCount(assessSo);
        PageHelp<AssessDto> pageHelp = new PageHelp<>();
        pageHelp.setCount(count);
        pageHelp.setData(list);
        return pageHelp;
    }

    @Override
    public Map<String, Integer> getCount(AssessSo assessSo) {
        int allCount = assessMapper.getCount(assessSo);
        assessSo.setType(3);
        int good = assessMapper.getCount(assessSo);
        assessSo.setType(2);
        int general = assessMapper.getCount(assessSo);
        assessSo.setType(1);
        int bad = assessMapper.getCount(assessSo);
        Map<String, Integer> map = new HashMap<>();
        map.put("allCount",allCount);
        map.put("good",good);
        map.put("general",general);
        map.put("bad",bad);
        return map;
    }
}

