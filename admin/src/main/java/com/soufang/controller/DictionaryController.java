package com.soufang.controller;

import com.soufang.Vo.AdminVo;
import com.soufang.Vo.dictionary.DictReqVo;
import com.soufang.Vo.dictionary.DictionaryVo;
import com.soufang.Vo.dictionary.UpDictReqVo;
import com.soufang.base.Result;
import com.soufang.base.dto.dictionary.DictionaryDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.dictionary.DictionarySo;
import com.soufang.feign.AdminDictionaryFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
@Controller
@RequestMapping(value = "admin/dictionary")
public class DictionaryController {


    @Autowired
    AdminDictionaryFeign adminDictionaryFeign;

    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String getList(){
        return "/dictionary/list";
    }

    @ResponseBody
    @RequestMapping(value = "getDictList", method = RequestMethod.GET)
    public DictionaryVo getDictList(String dictVDescription, String dictKDescription , Integer page , Integer limit) {
        DictionaryVo vo = new DictionaryVo();
        DictionarySo dictionarySo = new DictionarySo();
        if(StringUtils.isNotBlank(dictKDescription)){
            dictionarySo.setDictKDescription(dictKDescription);
        }else {
            dictionarySo.setDictKDescription("");
        }
        if(StringUtils.isNotBlank(dictVDescription)){
            dictionarySo.setDictVDescription(dictVDescription);
        }else {
            dictionarySo.setDictVDescription("");
        }
        dictionarySo.setLimit(limit);
        dictionarySo.setPage(page);
        PageHelp<DictionaryDto> pageHelp = adminDictionaryFeign.getDictList(dictionarySo);
        vo.setCount(pageHelp.getCount());
        vo.setData(pageHelp.getData());
        return vo;
    }

    @RequestMapping(value = "newDict", method = RequestMethod.GET)
    public String newDict(){
        return "/dictionary/new";
    }

    @ResponseBody
    @RequestMapping(value = "addToDict", method = RequestMethod.POST)
    public AdminVo addToDict(@RequestBody DictReqVo dictReqVo) {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setDictKDescription(dictReqVo.getDictKDescription());
        dictionaryDto.setDictKey(dictReqVo.getDictKey());
        dictionaryDto.setDictValue(dictReqVo.getDictValue());
        dictionaryDto.setDictVDescription(dictReqVo.getDictVDescription());
        dictionaryDto.setDictSort(dictReqVo.getDictSort());
        Result result = adminDictionaryFeign.addToDict(dictionaryDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id , ModelMap model){
        DictionaryDto dictionaryDto = adminDictionaryFeign.getDictDetailById(id);
        model.put("dto",dictionaryDto);
        return "/dictionary/update";
    }

    @ResponseBody
    @RequestMapping(value = "update/updateDict", method = RequestMethod.POST)
    public AdminVo updateDict(@RequestBody UpDictReqVo upDictReqVo) {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setDictId(upDictReqVo.getDictId());
        dictionaryDto.setDictKDescription(upDictReqVo.getDictKDescription());
        dictionaryDto.setDictKey(upDictReqVo.getDictKey());
        dictionaryDto.setDictValue(upDictReqVo.getDictValue());
        dictionaryDto.setDictVDescription(upDictReqVo.getDictVDescription());
        dictionaryDto.setDictSort(upDictReqVo.getDictSort());
        Result result = adminDictionaryFeign.updateDict(dictionaryDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @ResponseBody
    @RequestMapping(value = "delDictById/{id}", method = RequestMethod.GET)
    public AdminVo delDictById(@PathVariable Long id) {
        Result result = adminDictionaryFeign.delDictById(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }


}
