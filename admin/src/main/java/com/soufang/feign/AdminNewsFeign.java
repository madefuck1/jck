package com.soufang.feign;

import com.soufang.base.dto.news.NewsDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.news.NewsSo;
import com.soufang.base.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface AdminNewsFeign {

    @RequestMapping(value = "/core/news/getNews",method = RequestMethod.POST)
    PageHelp<NewsDto> getNews(@RequestBody NewsSo newsSo);

    @RequestMapping (value = "/core/news/getNewsById",method = RequestMethod.POST)
    NewsDto getNewsById(@RequestBody Long id);

    @RequestMapping (value = "/core/news/addNews",method = RequestMethod.POST)
     Result addNews(@RequestBody NewsDto newsDto);

    @RequestMapping(value = "/core/news/updateNews",method = RequestMethod.POST)
     Result updateNews(@RequestBody NewsDto newsDto);

    @RequestMapping (value = "/core/news/delNewsById",method = RequestMethod.POST)
     Result delNewsById(@RequestBody Long id);
}
