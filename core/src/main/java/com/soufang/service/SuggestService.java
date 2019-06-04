package com.soufang.service;

import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.base.search.suggest.SuggestSo;
import com.soufang.model.Suggest;

import java.util.List;

public interface SuggestService {
    /**
     * get 某条反馈记录
     * @return
     */
    SuggestDto getSuggest(Long id);

    /**
     * 查询所有反馈记录
     * @return
     */
    List<SuggestDto> getList(SuggestSo suggestSo);
    /**
     * 新增用户反馈
     * @param suggestDto
     * @return
     */
    int addSuggest(SuggestDto suggestDto);

    /**
     * 添加回复
     * @param suggestDto
     * @return
     */
    int addReturn(SuggestDto suggestDto);

    /**
     * 查询反馈记录的总数量
     * @return
     */
    int getCount();

    /**
     * 删除反馈
     * @param id
     * @return
     */
    int deleteById(Long id);
}
