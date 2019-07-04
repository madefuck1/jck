package com.soufang.app.controller;

import com.soufang.app.feign.AppConsultationFeign;
import com.soufang.app.vo.infromation.ConsultionListVo;
import com.soufang.app.vo.infromation.InformDetailVo;
import com.soufang.base.dto.news.NewsDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.news.NewsSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("app/consultation/")
public class AppConsultationController extends AppBaseController{
    @Autowired
    AppConsultationFeign consultationFeign;

    /**
     * 纺织资讯查询列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selConsultionList",method = RequestMethod.POST)
    public ConsultionListVo selInformList(@RequestBody InformDetailVo informDetailVo){
        ConsultionListVo consultionListVo=new ConsultionListVo();
        NewsSo newsSo =new NewsSo();
        newsSo.setLimit(informDetailVo.getLimit());
        newsSo.setPage(informDetailVo.getPage());
        newsSo.setId(informDetailVo.getId());
        //所有合集
        List<NewsDto> allLists=new ArrayList<>();
        //头条
        newsSo.setOrigin("1");
        PageHelp<NewsDto> headlinesLists = consultationFeign.getNews(newsSo);
        //行业
        newsSo.setOrigin("2");
        PageHelp<NewsDto> industryLists = consultationFeign.getNews(newsSo);
        //政府
        newsSo.setOrigin("3");
        PageHelp<NewsDto> governmentLists = consultationFeign.getNews(newsSo);
        //热点
        newsSo.setOrigin("4");
        PageHelp<NewsDto> HotLists = consultationFeign.getNews(newsSo);
        //图片
        newsSo.setOrigin("5");
        PageHelp<NewsDto> photoLists = consultationFeign.getNews(newsSo);

        allLists.addAll(headlinesLists.getData());
        allLists.addAll(industryLists.getData());
        allLists.addAll(governmentLists.getData());
        allLists.addAll(HotLists.getData());
        allLists.addAll(photoLists.getData());

        consultionListVo.setData(allLists);
        consultionListVo.setSuccess(true);
        return consultionListVo;
    }

    /**
     * 纺织资讯详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selConsultionList/{id}",method = RequestMethod.POST)
    public ConsultionListVo selInformList(@PathVariable String id){
        ConsultionListVo consultionListVo=new ConsultionListVo();
        NewsSo newsSo =new NewsSo();
        newsSo.setId(Long.valueOf(id));
        PageHelp<NewsDto> pageHelp = consultationFeign.getNews(newsSo);
        consultionListVo.setData(pageHelp.getData());
        consultionListVo.setSuccess(true);
        consultionListVo.setMessage("提交审核");
        return consultionListVo;
    }


}
