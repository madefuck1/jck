package com.soufang.mapper;

import com.soufang.base.dto.news.NewsDto;
import com.soufang.model.News;

import java.util.List;


public interface NewsMapper {


    /**
     * 分页（条件）查询新闻
     * @return  新闻的集合
     */
    public List<NewsDto> getNews(News news);

    /**
     * 得到新闻总数量
     * @param news
     * @return
     */
    public int getCount(News news);

    /**
     * 通过id拿新闻信息
     * @param id
     * @return
     */
    public NewsDto getNewsById(Long id);

    /**
     * 添加新闻
     * @param news
     * @return
     */
    public int insertNews(News news);

    /**
     * 修改新闻
     * @param news
     * @return
     */
    public int updateNews(News news);

    /**
     * 通过Id删除新闻
     * @param id
     * @return
     */
    public int delNewsById(Long id);
}
