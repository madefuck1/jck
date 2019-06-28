/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: ExhibitionController
 * Author:   小三
 * Date:     2019/6/11 8:36
 * Description: 展会信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.controller;

import com.soufang.base.dto.news.NewsDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.news.NewsSo;
import com.soufang.feign.InformFeign;
import com.soufang.vo.infromation.InformDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈展会信息〉
 *
 * @author 小三
 * @create 2019/6/24
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "inform")
public class InformController {
    @Autowired
    InformFeign informFeign;

    /**
     * 跳转纺织资讯页面-加载轮播图片
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "toInform", method = RequestMethod.GET)
    public String toInform(HttpServletRequest request, ModelMap model) {
        NewsSo newsSo=new NewsSo();
        newsSo.setPage(null);
        PageHelp<NewsDto> pageHelp = informFeign.getNews(newsSo);
        List<NewsDto> newsDtos =pageHelp.getData();
        List<NewsDto> newsPhotos=new ArrayList<>();
        NewsDto newsPhotoDto= new NewsDto();
        for (NewsDto newsDto :newsDtos){
            if(newsDto.getOrigin().equals("1")){
                newsPhotoDto.setPicture(newsDto.getPicture());
                newsPhotos.add(newsPhotoDto);
            }
        }
        if (newsPhotos.size() > 5) {//判断list长度
            List newList = newsPhotos.subList(0, 4);//取前四条数据
            model.put("newsPhotos",newList);
        } else{
            model.put("newsPhotos",newsPhotos);
        }
        return "information/informList";
    }

    /**
     * 跳转到详情页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "toInformDetail", method = RequestMethod.GET)
    public String toInformDetail(String id, ModelMap model) {
        model.put("id",id);
        return "information/informDetail";
    }

    /**
     * 查询列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selInformList",method = RequestMethod.POST)
    public PageHelp<NewsDto> selInformList(@RequestBody InformDetailVo informDetailVo){
        NewsSo newsSo =new NewsSo();
        newsSo.setLimit(informDetailVo.getLimit());
        newsSo.setPage(informDetailVo.getPage());
        newsSo.setId(informDetailVo.getId());
        PageHelp<NewsDto> pageHelp = informFeign.getNews(newsSo);
        return pageHelp;
    }

}
