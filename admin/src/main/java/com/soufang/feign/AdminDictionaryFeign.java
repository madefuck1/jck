package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.dictionary.DictionaryDto;
import com.soufang.base.search.dictionary.DictionarySo;
import com.soufang.base.page.PageHelp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
@FeignClient("core")
public interface AdminDictionaryFeign {


    @RequestMapping(value = "/core/dictionary/getDictList",method = RequestMethod.POST)
    PageHelp<DictionaryDto> getDictList(@RequestBody DictionarySo dictionarySo);

    @RequestMapping(value = "/core/dictionary/addToDict", method = RequestMethod.POST)
    Result addToDict(@RequestBody DictionaryDto dictionaryDto);

    @RequestMapping(value = "/core/dictionary/delDictById", method = RequestMethod.POST)
    Result delDictById(@RequestBody Long id);

    @RequestMapping(value = "/core/dictionary/getDictDetailById", method = RequestMethod.POST)
    DictionaryDto getDictDetailById(@RequestBody Long dictId);

    @RequestMapping(value = "/core/dictionary/updateDict", method = RequestMethod.POST)
    Result updateDict(@RequestBody DictionaryDto dictionaryDto);
}
