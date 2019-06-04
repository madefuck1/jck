package com.soufang.controller;

import com.soufang.feign.AdminCommonPullDownFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 下拉选择框
 */
@Controller
@RequestMapping(value = "/admin/commonPullDown")

public class CommonPullDownController {

    @Autowired
    AdminCommonPullDownFeign adminCommonPullDownFeign;

    // 店铺名称、店铺id
    @ResponseBody
    @RequestMapping(value = "getShopIdAndName", method = RequestMethod.GET)
    public List<Map<String, Object>> getShopIdAndName() {
        List<Map<String, Object>> list = adminCommonPullDownFeign.getShopIdAndName();
        return list;

    }

    /**
     * 分类 树状结构
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAssortIdAndName", method = RequestMethod.GET)
    public List<Map<String, Object>> getAssortIdAndName() {
        List<Map<String, Object>> mapList = adminCommonPullDownFeign.getAssortIdAndName();
        return mapList;
    }

}
