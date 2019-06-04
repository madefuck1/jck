package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppDictionaryFeign;
import com.soufang.app.vo.dictionary.DictionaryReqVo;
import com.soufang.app.vo.dictionary.DictionaryVo;
import com.soufang.base.dto.dictionary.DictionaryDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
@Controller
@RequestMapping(value = "app/dictionary")
public class AppDictionaryController {


    @Autowired
    AppDictionaryFeign appDictionaryFeign;

    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getDictList", method = RequestMethod.POST)
    public DictionaryVo getDictList(@RequestBody DictionaryReqVo dictionaryReqVo) {
        DictionaryVo vo = new DictionaryVo();
        if(StringUtils.isNotBlank(dictionaryReqVo.getKey())){
            List<DictionaryDto> data = appDictionaryFeign.getListByDictKey(dictionaryReqVo.getKey());
            vo.setData(data);
        }else {
            vo.setSuccess(false);
            vo.setMessage("缺少参数");
        }
        return vo;
    }
}
