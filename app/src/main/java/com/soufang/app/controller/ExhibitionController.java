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
package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.ExhibitionFeign;
import com.soufang.app.vo.exhibition.ExhibitionDetailVo;
import com.soufang.app.vo.exhibition.ExhibitionListVo;
import com.soufang.base.dto.exhibition.ExhibitionDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.exhibition.ExhibitionSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br> 
 * 〈展会信息〉
 *
 * @author 小三
 * @create 2019/6/24
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "app/exhibition/")
public class ExhibitionController extends AppBaseController{
    @Autowired
    ExhibitionFeign exhibitionFeign;

    /**
     * 查询列表/共用详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selExhibitionList",method = RequestMethod.POST)
    public ExhibitionListVo selExhibitionList(@RequestBody ExhibitionSo exhibitionSo){
        ExhibitionListVo enquiryListVo=new ExhibitionListVo();
        PageHelp<ExhibitionDto> pageHelp =exhibitionFeign.selExhibitionList(exhibitionSo);
        enquiryListVo.setData(pageHelp.getData());
        enquiryListVo.setSuccess(true);
        enquiryListVo.setMessage("提交审核");
        return enquiryListVo;
    }
    /**
     * 详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selExhibitionList/{exhibitionId}",method = RequestMethod.POST)
    public ExhibitionListVo selExhibitionList(@RequestBody String exhibitionId){
        ExhibitionListVo enquiryListVo=new ExhibitionListVo();
        ExhibitionSo exhibitionSo= new ExhibitionSo();
        exhibitionSo.setExhibitionId(Integer.valueOf(exhibitionId));
        PageHelp<ExhibitionDto> pageHelp =exhibitionFeign.selExhibitionList(exhibitionSo);
        enquiryListVo.setData(pageHelp.getData());
        enquiryListVo.setSuccess(true);
        enquiryListVo.setMessage("提交审核");
        return enquiryListVo;
    }

}
