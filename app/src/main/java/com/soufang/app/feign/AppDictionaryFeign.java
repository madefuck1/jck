package com.soufang.app.feign;

import com.soufang.base.dto.dictionary.DictionaryDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
@FeignClient("core")
public interface AppDictionaryFeign {


    @RequestMapping(value = "/core/dictionary/getListByDictKey",method = RequestMethod.POST)
    List<DictionaryDto> getListByDictKey(@RequestBody String dictKey);

}
