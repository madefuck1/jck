package com.soufang.service;

import com.soufang.base.dto.news.NewsDto;
import com.soufang.base.search.news.NewsSo;


import java.util.List;

public interface NewsService {
    /**
     * 分页（条件）查询新闻
     * @return  新闻的集合
     */
    public List<NewsDto> getNews(NewsSo newsSo);

    public List<NewsDto> getList(NewsDto newsDto);

    /**
     * 得到新闻总数量
     * @param newsSo
     * @return
     */
    public int getCount(NewsSo newsSo);

    /**
     * 通过id拿新闻信息
     * @param id
     * @return
     */
    public NewsDto getNewsById(Long id);

    /**
     * 添加新闻
     * @param newsDto
     * @return
     */
    public int insertNews(NewsDto newsDto);

    /**
     * 修改新闻
     * @param newsDto
     * @return
     */
    public int updateNews(NewsDto newsDto);

    /**
     * 通过Id删除新闻
     * @param id
     * @return
     */
    public int delNewsById(Long id);
}
