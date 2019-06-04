package com.soufang.controller;

import com.soufang.Vo.AdminVo;
import com.soufang.Vo.assort.AssortReqVo;
import com.soufang.Vo.assort.AssortVo;
import com.soufang.Vo.assort.UpAssortReqVo;
import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.AdminAssortFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Auther: chen
 * @Date: 2019/4/19
 * @Description:
 */
@Controller
@RequestMapping(value = "admin/assort")
public class AssortController {

    @Autowired
    AdminAssortFeign adminAssortFeign;


    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String toList(){
        return "/assort/list";
    }

    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public AssortVo getList() {
        AssortVo vo = new AssortVo();
        vo.setData(adminAssortFeign.getAll());
        vo.setCode("0");
        return vo;
    }

    @RequestMapping(value = "newAssort", method = RequestMethod.GET)
    public String newAssort(ModelMap model){
        model.put("list",adminAssortFeign.getAll());
        return "/assort/new";
    }

    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "addAssort", method = RequestMethod.POST)
    public AdminVo addAssort(@RequestBody AssortReqVo assortReqVo) {
        AssortDto assortDto = new AssortDto();
        if(0 == assortReqVo.getParentId()){
            assortDto.setParentId(assortReqVo.getParentId());
            assortDto.setAssortLevel(1);
        }else {
            AssortDto parent = adminAssortFeign.detail(assortReqVo.getParentId());
            assortDto.setParentId(assortReqVo.getParentId());
            assortDto.setAssortLevel(parent.getAssortLevel()+1);
        }
        assortDto.setAssortName(assortReqVo.getAssortName());
        assortDto.setSort(assortReqVo.getSort());
        if(StringUtils.isNotBlank(assortReqVo.getKey1())){
            assortDto.setKey1(assortReqVo.getKey1());
        }
        if(StringUtils.isNotBlank(assortReqVo.getKey2())){
            assortDto.setKey2(assortReqVo.getKey2());
        }
        if(StringUtils.isNotBlank(assortReqVo.getKey3())){
            assortDto.setKey3(assortReqVo.getKey3());
        }
        if(StringUtils.isNotBlank(assortReqVo.getKey4())){
            assortDto.setKey4(assortDto.getKey4());
        }
        if(StringUtils.isNotBlank(assortReqVo.getKey5())){
            assortDto.setKey5(assortReqVo.getKey5());
        }
        Result result = adminAssortFeign.save(assortDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id , ModelMap model){
        AssortDto assortDto = adminAssortFeign.detail(id);
        model.put("dto",assortDto);
        ArrayList<AssortDto> assortDtoList = (ArrayList<AssortDto>) adminAssortFeign.getAll() ;
        assortDtoList.remove(assortDto);
        model.put("list",assortDtoList);
        return "/assort/update";
    }

    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "update/updateAssort", method = RequestMethod.POST)
    public AdminVo updateAssort(@RequestBody UpAssortReqVo upAssortReqVo) {
        AssortDto assortDto = new AssortDto();
        assortDto.setAssortId(upAssortReqVo.getAssortId());
        if(0 == upAssortReqVo.getParentId()){
            assortDto.setParentId(upAssortReqVo.getParentId());
            assortDto.setAssortLevel(1);
        }else {
            assortDto.setParentId(upAssortReqVo.getParentId());
            AssortDto parent = adminAssortFeign.detail(upAssortReqVo.getParentId());
            assortDto.setAssortLevel(parent.getAssortLevel()+1);
        }
        assortDto.setAssortName(upAssortReqVo.getAssortName());
        assortDto.setSort(upAssortReqVo.getSort());
        if(StringUtils.isNotBlank(upAssortReqVo.getKey1())){
            assortDto.setKey1(upAssortReqVo.getKey1());
        }
        if(StringUtils.isNotBlank(upAssortReqVo.getKey2())){
            assortDto.setKey2(upAssortReqVo.getKey2());
        }
        if(StringUtils.isNotBlank(upAssortReqVo.getKey3())){
            assortDto.setKey3(upAssortReqVo.getKey3());
        }
        if(StringUtils.isNotBlank(upAssortReqVo.getKey4())){
            assortDto.setKey4(assortDto.getKey4());
        }
        if(StringUtils.isNotBlank(upAssortReqVo.getKey5())){
            assortDto.setKey5(upAssortReqVo.getKey5());
        }
        Result result = adminAssortFeign.update(assortDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public AdminVo delete(@PathVariable Long id) {
        Result result = adminAssortFeign.delete(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }
}
