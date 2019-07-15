package com.soufang.feign;

import com.soufang.base.dto.news.NewsDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.news.NewsSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface InformFeign {
    //列表查询
    @RequestMapping(value = "core/news/getNews",method = RequestMethod.POST)
    PageHelp<NewsDto> getNews(@RequestBody NewsSo newsSo);
}
