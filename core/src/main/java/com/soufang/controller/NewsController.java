package com.soufang.controller;

import com.github.pagehelper.PageHelper;
import com.soufang.base.dto.news.NewsDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.news.NewsSo;
import com.soufang.base.Result;
import com.soufang.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/news")
public class NewsController {


    @Autowired
    NewsService newsService;

    /**
     * 查询所有
     * @param newsSo
     * @return
     */
    @RequestMapping (value = "getNews",method = RequestMethod.POST)
    public PageHelp<NewsDto> getNews(@RequestBody NewsSo newsSo){
        PageHelp<NewsDto> pageHelp = new PageHelp<>();
        if(!(newsSo.getPage()==null)){
            PageHelper.startPage(newsSo.getPage(),newsSo.getLimit());
        }
        List<NewsDto> list = newsService.getNews(newsSo);
        int count = newsService.getCount(newsSo);
        pageHelp.setCount(count);
        pageHelp.setData(list);
        return pageHelp;
    }

    /**
     * 由id得到 具体新闻信息
     * @param id
     * @return
     */
    @RequestMapping (value = "getNewsById",method = RequestMethod.POST)
    public NewsDto getNewsById(@RequestBody Long id){
        return newsService.getNewsById(id);
    }

    /**
     * 添加
     * @param newsDto
     * @return
     */
    @RequestMapping (value = "addNews",method = RequestMethod.POST)
    public Result addNews(@RequestBody NewsDto newsDto){
        Result result = new Result();
        List<NewsDto> list = newsService.getList(newsDto);
        if(list != null && list.size() > 0){
             result.setSuccess(false);
             result.setMessage("新闻已存在");
        }else {
            newsService.insertNews(newsDto);
            result.setSuccess(true);
            result.setMessage("添加成功");
        }
        return result;
    }

    /**
     * 更新
     * @param newsDto
     * @return
     */
    @RequestMapping(value = "updateNews",method = RequestMethod.POST)
    public Result updateNews(@RequestBody NewsDto newsDto){
        Result result = new Result();
        if( newsService.updateNews(newsDto) > 0 ){
            result.setMessage("更新成功");
            result.setSuccess(true);
        }else {
            result.setMessage("更新失败");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping (value = "delNewsById",method = RequestMethod.POST)
    public Result delNewsById(@RequestBody Long id){
        Result result = new Result();
        if(newsService.delNewsById(id) > 0){
            result.setMessage("删除成功");
            result.setSuccess(true);
        }else {
            result.setMessage("删除失败");
            result.setSuccess(false);
        }
        return result;
    }


}
