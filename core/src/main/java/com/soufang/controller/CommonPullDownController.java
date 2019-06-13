package com.soufang.controller;


import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.search.assort.AssortSo;
import com.soufang.model.Assort;
import com.soufang.service.AssortService;
import com.soufang.service.CommonPullDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 下拉选择框
 */
@RestController
@RequestMapping(value = "/core/commonPullDown")
public class CommonPullDownController {

    @Autowired
    CommonPullDownService commonPullDownService;

    @Autowired
    AssortService assortService;

    // 店铺名称、店铺id
    @RequestMapping(value = "getShopIdAndName", method = RequestMethod.POST)
    public List<Map<String,Object>> getShopIdAndName() {
        List<Map<String,Object>> mapListOption = commonPullDownService.getShopIdAndNameOption();
        return mapListOption;
    }

    // 分类id 分类名称
    @RequestMapping(value = "getAssortIdAndName",method = RequestMethod.POST)
    public List<Map<String, Object>> getAssortIdAndName(){
        List<Map<String,Object>> mapListOption = commonPullDownService.getAssortIdAndName();
        return mapListOption;
    }


    //获取顶级分类
    @RequestMapping(value = "getFirstAssort",method = RequestMethod.POST)
    public List<AssortDto> getFirstAssort(){
        List<AssortDto> mapListOption = commonPullDownService.getFirstAssort(0L);
        return mapListOption;
    }

    //获取二三级分类
    @RequestMapping(value = "getAssortByParentId",method = RequestMethod.POST)
    public List<AssortDto> getAssortByParentId(@RequestBody Long parentId){
        return commonPullDownService.getAssortByParentId(parentId);
    }


    //获取下级分类
    @RequestMapping(value = "getDownList",method = RequestMethod.POST)
    public List<AssortDto> getDownList(@RequestBody Long parentId){
        AssortDto assort = new AssortDto();
        assort.setParentId(parentId);
        return assortService.getList(assort);
    }


    //获取次级分类
    @RequestMapping(value = "getAssortAByParentId",method = RequestMethod.POST)
    public List<AssortDto> getAssortAByParentId(@RequestBody Long parentId){
        return commonPullDownService.getAssortAByParentId(parentId);
    }

    /**
     * 根据当前ID查询下面所有ID
     * @param assortId
     * @return
     */
    @RequestMapping(value = "selUnderAssort",method = RequestMethod.POST)
    public List<AssortDto> selUnderAssort(@RequestBody Long assortId){
        return commonPullDownService.selUnderAssort(assortId);
    }

    @RequestMapping(value = "getAssortByKey", method = RequestMethod.POST)
    public Map<String,Object> getAssortByKey(@RequestBody AssortSo so){
        return assortService.getAssortByKey(so);
    }


}
