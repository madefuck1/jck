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

import com.soufang.base.dto.exhibition.ExhibitionDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.exhibition.ExhibitionSo;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.ExhibitionFeign;
import com.soufang.vo.exhibition.ExhibitionDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉<br> 
 * 〈展会信息〉
 *
 * @author 小三
 * @create 2019/6/11
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "exhibition")
public class ExhibitionController {
    @Autowired
    ExhibitionFeign exhibitionFeign ;
    /**
     * 跳转展会信息页面
     * @param request
     * @param model
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "toExhibition", method = RequestMethod.GET)
    public String toExhibition(HttpServletRequest request, ModelMap model) {
        return "exhibition/exhibition";
    }

    /**
     * 跳转到详情页面
     * @param exhibitionId
     * @param model
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "toExhibitionDetail", method = RequestMethod.GET)
    public String toExhibitionDetail(String exhibitionId, ModelMap model) {
        model.put("exhibitionId",exhibitionId);
        return "exhibition/exhibitionDetail";
    }

    /**
     * 查询列表
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selExhibitionList",method = RequestMethod.POST)
    public PageHelp<ExhibitionDto> selExhibitionList(@RequestBody ExhibitionDetailVo exhibitionDetailVo){
        ExhibitionSo exhibitionSo =new ExhibitionSo();
        exhibitionSo.setExhibitionId(exhibitionDetailVo.getExhibitionId());
        PageHelp<ExhibitionDto> pageHelp =exhibitionFeign.selExhibitionList(exhibitionSo);
        return pageHelp;
    }

}
