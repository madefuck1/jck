package com.soufang.service.impl;

import com.soufang.base.dto.dictionary.DictionaryDto;
import com.soufang.base.search.dictionary.DictionarySo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.DictionaryMapper;
import com.soufang.model.Dictionary;
import com.soufang.service.DictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/20
 * @Description:
 */
@Service(value = "dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    DictionaryMapper dictionaryMapper;

    @Override
    public int deleteByPrimaryKey(Long dictId) {
        return dictionaryMapper.deleteByPrimaryKey(dictId);
    }

    @Override
    public int insertSelective(DictionaryDto record) {
        Dictionary dictionary = new Dictionary();
        BeanUtils.copyProperties(record,dictionary);
        dictionary.setCreateTime(DateUtils.getToday());
        dictionaryMapper.insertSelective(dictionary);
        return 1;
    }

    @Override
    public DictionaryDto selectByPrimaryKey(Long dictId) {
        Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(dictId);
        DictionaryDto dictionaryDto = new DictionaryDto();
        BeanUtils.copyProperties(dictionary,dictionaryDto);
        return dictionaryDto;
    }

    @Override
    public int updateByPrimaryKeySelective(DictionaryDto record) {
        Dictionary dictionary = new Dictionary();
        BeanUtils.copyProperties(record,dictionary);
        dictionaryMapper.updateByPrimaryKeySelective(dictionary);
        return 1;
    }

    @Override
    public List<DictionaryDto> getList(DictionarySo dict) {
        List<Dictionary> list = dictionaryMapper.getList(dict);
        List<DictionaryDto> dictionaryDtos = new ArrayList<>();
        for(Dictionary dictionary1 : list){
            DictionaryDto dictionaryDto = new DictionaryDto();
            BeanUtils.copyProperties(dictionary1,dictionaryDto);
            dictionaryDtos.add(dictionaryDto);
        }
        return dictionaryDtos;
    }

    @Override
    public int getCount(DictionarySo dic) {
        return dictionaryMapper.getCount(dic);
    }

    @Override
    public List<DictionaryDto> getByKeyAndValue(DictionaryDto d) {
        Dictionary dictionary = new Dictionary();
        BeanUtils.copyProperties(d,dictionary);
        List<Dictionary> list = dictionaryMapper.getByKeyAndValue(dictionary);
        List<DictionaryDto> dictionaryDtos = new ArrayList<>();
        for(Dictionary dictionary1 : list){
            DictionaryDto dictionaryDto = new DictionaryDto();
            BeanUtils.copyProperties(dictionary1,dictionaryDto);
            dictionaryDtos.add(dictionaryDto);
        }
        return dictionaryDtos;
    }

    @Override
    public List<DictionaryDto> selectByDictKey(String dictKey) {
        List<Dictionary> list = dictionaryMapper.selectByDictKey(dictKey);
        List<DictionaryDto> dictionaryDtos = new ArrayList<>();
        for(Dictionary dictionary1 : list){
            DictionaryDto dictionaryDto = new DictionaryDto();
            BeanUtils.copyProperties(dictionary1,dictionaryDto);
            dictionaryDtos.add(dictionaryDto);
        }
        return dictionaryDtos;
    }
}
