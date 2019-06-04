package com.soufang.mapper;

import com.soufang.base.search.dictionary.DictionarySo;
import com.soufang.model.Dictionary;

import java.util.List;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Long dictId);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(Dictionary record);

    List<Dictionary> getList(DictionarySo dict);

    int getCount(DictionarySo dic);

    List<Dictionary> getByKeyAndValue(Dictionary d);

    List<Dictionary> selectByDictKey(String dictKey);
}