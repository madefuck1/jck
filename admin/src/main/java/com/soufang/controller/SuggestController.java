package com.soufang.controller;


import com.soufang.Vo.AdminVo;
import com.soufang.Vo.suggest.AddReturnVo;
import com.soufang.Vo.suggest.SugReqVo;
import com.soufang.Vo.suggest.SuggestVo;
import com.soufang.base.Result;
import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.suggest.SuggestSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.feign.AdminSuggestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "admin/suggest")
public class SuggestController {

    @Autowired
    AdminSuggestFeign adminSuggestFeign;

    @RequestMapping( value = "toList",method = RequestMethod.GET)
    public String getList(){
        return "/suggest/list";
    }


    @ResponseBody
    @RequestMapping(value = "getSuggestList",method = RequestMethod.GET)
    public SuggestVo getSuggestList(Integer limit, Integer page){
        SuggestVo suggestVo  = new SuggestVo();
        SuggestSo suggestSo = new SuggestSo();
        suggestSo.setLimit(limit);
        suggestSo.setPage(page);
        PageHelp<SuggestDto> pageHelp = adminSuggestFeign.getList(suggestSo);
        suggestVo.setData(pageHelp.getData());
        suggestVo.setCount(pageHelp.getCount());
        return suggestVo;
    }
    @RequestMapping( value = "toAddSuggest",method = RequestMethod.GET)
    public String toAddSuggest(){
        return "/suggest/addSuggest";
    }

    @ResponseBody
    @RequestMapping(value = "addSuggest",method = RequestMethod.POST)
    public AdminVo addSuggest(@RequestBody SugReqVo sugReqVo){
        SuggestDto sugDto = new SuggestDto();
        sugDto.setUserId(sugReqVo.getUserId());
        sugDto.setSugContent(sugReqVo.getSugContent());
        sugDto.setRenContent(sugReqVo.getRenContent());
        sugDto.setCreateTime(DateUtils.getToday());
        Result result = adminSuggestFeign.addSuggest(sugDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }


    @RequestMapping( value = "toAddReturn/{id}",method = RequestMethod.GET)
    public String toAddReturn(@PathVariable Long id , ModelMap model){
        SuggestDto suggestDto = adminSuggestFeign.getSuggest(id);
        model.put("suggestDto",suggestDto);
        return "/suggest/addReturn";
    }

    @ResponseBody
    @RequestMapping(value = "toAddReturn/addReturn",method = RequestMethod.POST)
    public AdminVo addReturn(@RequestBody AddReturnVo addReturnVo){

        SuggestDto sugDto = new SuggestDto();
        sugDto.setSugId(addReturnVo.getSugId());
        sugDto.setRenContent(addReturnVo.getRenContent());
        sugDto.setReturnTime(DateUtils.getToday());
        Result result = adminSuggestFeign.addReturn(sugDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @ResponseBody
    @RequestMapping(value = "deleteById/{id}", method = RequestMethod.GET)
    public AdminVo deleteById(@PathVariable Long id){
        Result result = adminSuggestFeign.deleteById(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }
}
