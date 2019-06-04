package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.dto.news.NewsDto;
import com.soufang.base.search.news.NewsSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.NewsMapper;
import com.soufang.model.News;
import com.soufang.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "newsService")
public class NewsServiceImpl implements NewsService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NewsMapper newsMapper;

    @Override
    public List<NewsDto> getNews(NewsSo newsSo) {
        News news = new News();
        BeanUtils.copyProperties(newsSo,news);
        List<NewsDto> list= newsMapper.getNews(news);
        return list;
    }

    @Override
    public List<NewsDto> getList(NewsDto newsDto) {
        News news = new News();
        BeanUtils.copyProperties(newsDto,news);
        List<NewsDto> list= newsMapper.getNews(news);
        return list;
    }

    @Override
    public int getCount(NewsSo newsSo) {
        News news = new News();
        BeanUtils.copyProperties(newsSo,news);
        return newsMapper.getCount(news);
    }

    @Override
    public NewsDto getNewsById(Long id) {
        return newsMapper.getNewsById(id);
    }

    @Override
    @Transactional
    public int insertNews(NewsDto newsDto) {
        try {
            newsDto.setCreateTime(DateUtils.getToday());
            News news = new News();
            BeanUtils.copyProperties(newsDto,news);
            newsMapper.insertNews(news);
            return 1;
        }catch (Exception e){
            logger.info("添加新闻失败："+e.toString());
            throw new BusinessException("添加新闻失败");
        }
    }

    @Override
    public int updateNews(NewsDto newsDto) {
        newsDto.setCreateTime(DateUtils.getToday());
        News news = new News();
        BeanUtils.copyProperties(newsDto,news);
        newsMapper.updateNews(news);
        return 1;
    }

    @Override
    public int delNewsById(Long id) {
        newsMapper.delNewsById(id);
        return 1;
    }
}
