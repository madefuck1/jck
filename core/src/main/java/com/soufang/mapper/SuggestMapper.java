package com.soufang.mapper;

import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.model.Suggest;

import java.util.List;

public interface SuggestMapper {

    /**
     * get 某条反馈记录
     * @return
     */
    SuggestDto getSuggest(Long id);
    /**
     * 新增用户反馈
     * @param suggest
     * @return
     */
    int addSuggest(Suggest suggest);

    /**
     * 添加回复
     * @param suggest
     * @return
     */
    int addReturn(Suggest suggest);
    /**
     * 查询所有反馈记录
     * @return
     */
    List<SuggestDto> getList();

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
