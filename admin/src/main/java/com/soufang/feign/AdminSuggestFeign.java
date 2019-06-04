package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.suggest.SuggestSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("core")
public interface AdminSuggestFeign {
    @RequestMapping(value = "/core/suggest/getSuggest",method = RequestMethod.POST)
    SuggestDto getSuggest(@RequestBody Long id);

    @RequestMapping(value = "/core/suggest/getList",method = RequestMethod.POST)
    PageHelp<SuggestDto> getList(@RequestBody SuggestSo suggestSo);

    @RequestMapping(value = "/core/suggest/addSuggest",method = RequestMethod.POST)
    Result addSuggest(@RequestBody SuggestDto suggestDto);

    @RequestMapping(value = "/core/suggest/addReturn",method = RequestMethod.POST)
    Result addReturn(@RequestBody SuggestDto suggestDto);

    @RequestMapping(value = "/core/suggest/deleteById", method = RequestMethod.POST)
    Result deleteById(@RequestBody Long id);
}
