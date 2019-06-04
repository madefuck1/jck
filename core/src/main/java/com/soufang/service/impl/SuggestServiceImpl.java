package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.base.search.suggest.SuggestSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.SuggestMapper;
import com.soufang.model.Suggest;
import com.soufang.service.SuggestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "suggestService")
public class SuggestServiceImpl implements SuggestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SuggestMapper suggestMapper;

    @Override
    public SuggestDto getSuggest(Long id) {
        return suggestMapper.getSuggest(id);
    }

    @Override
    public List<SuggestDto> getList(SuggestSo suggestSo) {
        Suggest suggest = new Suggest();
        BeanUtils.copyProperties(suggestSo,suggest);
        List<SuggestDto> list = suggestMapper.getList();
        return list;
    }

    @Override
    @Transactional
    public int addSuggest(SuggestDto suggestDto) {
        try {
            suggestDto.setCreateTime(DateUtils.getToday());
            Suggest suggest = new Suggest();
            BeanUtils.copyProperties(suggestDto,suggest);
            suggestMapper.addSuggest(suggest);
            return 1;
        }catch (Exception e){
            logger.info("添加反馈失败："+e.toString());
            throw new BusinessException("添加反馈失败");
        }

    }

    @Override
    @Transactional
    public int addReturn(SuggestDto suggestDto) {
        try {
            suggestDto.setReturnTime(DateUtils.getToday());
            Suggest suggest = new Suggest();
            BeanUtils.copyProperties(suggestDto,suggest);
            suggestMapper.addReturn(suggest);
            return 1;
        }catch (Exception e){
            logger.info("添加反馈失败："+e.toString());
            throw new BusinessException("添加反馈失败");
        }
    }

    @Override
    public int getCount() {
        return suggestMapper.getCount();
    }

    @Override
    public int deleteById(Long id) {
        suggestMapper.deleteById(id);
        return 1;
    }
}
