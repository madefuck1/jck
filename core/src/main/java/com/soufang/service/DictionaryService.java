package com.soufang.service;

import com.soufang.base.dto.dictionary.DictionaryDto;
import com.soufang.base.search.dictionary.DictionarySo;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/20
 * @Description:
 */
public interface DictionaryService {

    int deleteByPrimaryKey(Long dictId);

    int insertSelective(DictionaryDto record);

    DictionaryDto selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(DictionaryDto record);

    List<DictionaryDto> getList(DictionarySo dict);

    int getCount(DictionarySo dic);

    List<DictionaryDto> getByKeyAndValue(DictionaryDto d);

    List<DictionaryDto> selectByDictKey(String dictKey);
}
