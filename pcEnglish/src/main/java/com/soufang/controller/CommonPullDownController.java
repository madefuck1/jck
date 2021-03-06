package com.soufang.controller;


import com.soufang.base.dto.assort.AssortDto;
import com.soufang.feign.CommonPullDownFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 下拉选择框
 */
@Controller
@RequestMapping(value = "/pc/commonPullDown")
public class CommonPullDownController {


    @Autowired
    CommonPullDownFeign commonPullDownFeign;

    @ResponseBody
    @RequestMapping(value = "getAssortByParentId/{parentId}", method = RequestMethod.GET)
    public List<AssortDto> getAssortByParentId(@PathVariable Long parentId) {
        List<AssortDto> mapListOption = commonPullDownFeign.getAssortByParentId(parentId);
        return mapListOption;
    }

    @ResponseBody
    @RequestMapping(value = "getAssortAByParentId/{parentId}", method = RequestMethod.GET)
    public List<AssortDto> getAssortAByParentId(@PathVariable Long parentId) {
        List<AssortDto> mapListOption = commonPullDownFeign.getAssortAByParentId(parentId);
        return mapListOption;
    }

    @ResponseBody
    @RequestMapping(value = "selUnderAssort/{assortId}", method = RequestMethod.GET)
    public List<AssortDto> selUnderAssort(@PathVariable Long assortId) {
        List<AssortDto> mapListOption = commonPullDownFeign.selUnderAssort(assortId);
        return mapListOption;
    }

}
