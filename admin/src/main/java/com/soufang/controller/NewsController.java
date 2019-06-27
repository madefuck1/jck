package com.soufang.controller;


import com.soufang.Vo.AdminVo;
import com.soufang.Vo.news.NewsReqVo;
import com.soufang.Vo.news.NewsVo;
import com.soufang.Vo.news.UpNewsReqVo;
import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.news.NewsDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.news.NewsSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.Result;
import com.soufang.base.utils.FtpClient;
import com.soufang.feign.AdminNewsFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/news")
public class NewsController {
    @Value("${upload.news}")
    private String newsUrl;
    @Autowired
    AdminNewsFeign adminNewsFeign;

    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String getList(){
        return "/news/list";
    }

    @ResponseBody
    @RequestMapping(value = "getNewsList", method = RequestMethod.GET)
    public NewsVo getNewsList(String author, String content, String title, String origin, Integer limit, Integer page){
        NewsVo vo = new NewsVo();
        NewsSo newsSo = new NewsSo();
        if(StringUtils.isNotBlank(author)){
            newsSo.setAuthor(author);
        }
        if(StringUtils.isNotBlank(content)){
            newsSo.setContent(content);
        }
        if(StringUtils.isNotBlank(title)){
            newsSo.setTitle(title);
        }
        if(StringUtils.isNotBlank(origin)){
            newsSo.setOrigin(origin);
        }
        newsSo.setLimit(limit);
        newsSo.setPage(page);
        PageHelp<NewsDto> pageHelp = adminNewsFeign.getNews(newsSo);
        vo.setCount(pageHelp.getCount());
        vo.setData(pageHelp.getData());
        return vo;
    }

    @RequestMapping(value = "details/{id}", method = RequestMethod.GET)
    public String details(@PathVariable Long id , ModelMap model){
        NewsDto newsDto = adminNewsFeign.getNewsById(id);
        model.put("newsDto",newsDto);
        return "/news/details";
    }

    @RequestMapping(value = "toAddNews", method = RequestMethod.GET)
    public String toAddNews(){
        return "/news/addNews";
    }


    /**
     * 新增新闻资讯
     * @param newsReqVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addNews", method = RequestMethod.POST)
    public AdminVo addNews(@RequestBody NewsReqVo newsReqVo){
        NewsDto newsDto = new NewsDto();
        newsDto.setTitle(newsReqVo.getTitle());
        newsDto.setAuthor(newsReqVo.getAuthor());
        newsDto.setContent(newsReqVo.getContent());
        newsDto.setOrigin(newsReqVo.getOrigin());
        newsDto.setCreateTime(DateUtils.getToday());
        newsDto.setPicture(newsReqVo.getPicture());
        Result result = adminNewsFeign.addNews(newsDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdateNews(@PathVariable Long id, ModelMap model){
        NewsDto newsDto = adminNewsFeign.getNewsById(id);
        model.put("newsDto",newsDto);
        return "/news/update";
    }

    @ResponseBody
    @RequestMapping(value = "update/updateNews", method = RequestMethod.POST)
    public AdminVo updateNews(@RequestBody UpNewsReqVo upNewsReqVo){
        NewsDto newsDto = new NewsDto();
        newsDto.setId(upNewsReqVo.getId());
        newsDto.setAuthor(upNewsReqVo.getAuthor());
        newsDto.setTitle(upNewsReqVo.getTitle());
        newsDto.setContent(upNewsReqVo.getContent());
        newsDto.setOrigin(upNewsReqVo.getOrigin());
        newsDto.setCreateTime(DateUtils.getToday());
        Result result = adminNewsFeign.updateNews(newsDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }
    @ResponseBody
    @RequestMapping(value = "delNewsById/{id}", method = RequestMethod.GET)
    public AdminVo delNewsById(@PathVariable Long id){
        Result result = adminNewsFeign.delNewsById(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    /**
     * 新闻资讯新增时上传图片
     * @param file
     */
    @ResponseBody
    @RequestMapping(value = "addNewsImg", method = RequestMethod.POST)
    public Object addNewsImg(@RequestParam("file")MultipartFile file) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = FtpClient.uploadImage(file, newsUrl);
        if ((boolean) map.get("success")) {
            Map<String, Object> result2 = new HashMap<String, Object>();
            String uploadNameUrl = String.valueOf(map.get("uploadName"));
            result2.put("src", PropertiesParam.file_pre+uploadNameUrl);
            result2.put("title",StringUtils.substringAfterLast(uploadNameUrl,"/"));
            result.put("code", 0);    //0表示上传成功
            result.put("msg", "上传成功"); //提示消息
            result.put("data",result2);
        } else {
            result.put("code", 1);
            result.put("msg", "上传失败");
        }
        return result;
    }

}
