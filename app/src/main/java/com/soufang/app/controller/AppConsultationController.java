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
        PageHelp<NewsDto> pageHelp = consultationFeign.getNews(newsSo);
        consultionListVo.setData(pageHelp.getData());
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
