package com.soufang.controller;


import com.github.pagehelper.PageHelper;
import com.soufang.base.Result;
import com.soufang.base.dto.dictionary.DictionaryDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.dictionary.DictionarySo;
import com.soufang.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/dictionary")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    /**
     * 后台接口
     */
    @RequestMapping(value = "getDictList",method = RequestMethod.POST)
    public PageHelp<DictionaryDto> getDictList(@RequestBody DictionarySo dictionarySo) {
        PageHelp<DictionaryDto> pageHelp = new PageHelp<>();
        PageHelper.startPage(dictionarySo.getPage(), dictionarySo.getLimit());
        List<DictionaryDto> list = dictionaryService.getList(dictionarySo);
        int count = dictionaryService.getCount(dictionarySo);
        pageHelp.setCount(count);
        pageHelp.setData(list);
        return pageHelp;
    }

    /**
     * 根据key  获取所有value
     * @param dictKey
     * @return
     */
    @RequestMapping(value = "getListByDictKey",method = RequestMethod.POST)
    public List<DictionaryDto> getListByDictKey(@RequestBody String dictKey) {
        return dictionaryService.selectByDictKey(dictKey);
    }

    @RequestMapping(value = "addToDict", method = RequestMethod.POST)
    public Result addToDict(@RequestBody DictionaryDto dictionaryDto) {
        Result result = new Result();
        List<DictionaryDto> list = dictionaryService.getByKeyAndValue(dictionaryDto);
        if(list != null && list.size() > 0){
            result.setSuccess(false);
            result.setMessage("字典已存在");
        }else {
            dictionaryService.insertSelective(dictionaryDto);
            result.setSuccess(true);
            result.setMessage("添加成功");
        }
        return result;
    }


    @RequestMapping(value = "delDictById", method = RequestMethod.POST)
    public Result delDictById(@RequestBody Long id) {
        Result result = new Result();
        if (dictionaryService.deleteByPrimaryKey(id) > 0) {
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "getDictDetailById", method = RequestMethod.POST)
    public DictionaryDto getDictDetailById(@RequestBody Long dictId) {
        return dictionaryService.selectByPrimaryKey(dictId);
    }

    @RequestMapping(value = "updateDict", method = RequestMethod.POST)
    public Result updateDict(@RequestBody DictionaryDto dictionaryDto) {
        Result result = new Result();
        if (dictionaryService.updateByPrimaryKeySelective(dictionaryDto) > 0) {
            result.setSuccess(true);
            result.setMessage("更新成功");
        } else {
            result.setSuccess(false);
        }
        return result;
    }
}
