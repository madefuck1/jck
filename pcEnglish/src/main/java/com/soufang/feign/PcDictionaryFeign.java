package com.soufang.feign;

import com.soufang.base.dto.dictionary.DictionaryDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("core")
public interface PcDictionaryFeign {
    //根据dictKey获取字典信息
    @RequestMapping(value = "core/dictionary/getListByDictKey",method = RequestMethod.POST)
    List<DictionaryDto> getListByDictKey(@RequestBody String dictKey);
}
