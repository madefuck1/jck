package com.soufang.controller;

import com.github.pagehelper.PageHelper;
import com.soufang.base.Result;
import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.suggest.SuggestSo;
import com.soufang.service.SuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/suggest")
public class SuggestController {
    @Autowired
    SuggestService suggestService;

    /**
     * 得到具体信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getSuggest",method = RequestMethod.POST)
    SuggestDto getSuggestDto (@RequestBody Long id){
        SuggestDto suggestDto = suggestService.getSuggest(id);
        return suggestDto;
    }

    /**
     * 得到所有
     * @param suggestSo
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    PageHelp<SuggestDto> getList(@RequestBody SuggestSo suggestSo){
        PageHelp<SuggestDto> pageHelp = new PageHelp<>();
        PageHelper.startPage(suggestSo.getPage(),suggestSo.getLimit());
        List<SuggestDto> list = suggestService.getList(suggestSo);
        int count = suggestService.getCount();
        pageHelp.setCount(count);
        pageHelp.setData(list);
        return pageHelp;
    }

    /**
     * 添加反馈记录
     * @param suggestDto
     * @return
     */
    @RequestMapping(value = "addSuggest",method = RequestMethod.POST)
    Result addSuggest(@RequestBody SuggestDto suggestDto){
        Result result = new Result();
        if(suggestService.addSuggest(suggestDto) > 0 ){
            result.setMessage("反馈成功");
            result.setSuccess(true);
        }else {
            result.setMessage("反馈失败");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     *添加回复记录
     * @param suggestDto
     * @return
     */
    @RequestMapping(value = "addReturn",method = RequestMethod.POST)
    Result addReturn(@RequestBody SuggestDto suggestDto){
        Result result = new Result();
        if(suggestService.addReturn(suggestDto) > 0 ){
            result.setMessage("回复成功");
            result.setSuccess(true);
        }else {
            result.setMessage("回复失败");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    Result deleteById(@RequestBody Long id){
        Result result = new Result();
        if(suggestService.deleteById(id) > 0){
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else {
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }
}
