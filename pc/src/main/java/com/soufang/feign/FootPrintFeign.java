package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.footprint.FootPrintDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.footPrint.FootPrintSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface FootPrintFeign {

    //新增
    @RequestMapping(value = "core/footPrint/addFootPrint",method = RequestMethod.POST)
    boolean addFootPrint(@RequestBody FootPrintDto footPrintDto);

    // 列表一周
    @RequestMapping(value = "core/footPrint/selFootPrintOneWeek",method = RequestMethod.POST)
    PageHelp<FootPrintDto> selFootPrintOneWeek(@RequestBody FootPrintSo footPrintSo);

    // 列表所有
    @RequestMapping(value = "core/footPrint/getFootPrintList",method = RequestMethod.POST)
    PageHelp<FootPrintDto> getFootPrintList(@RequestBody FootPrintSo footPrintSo);

    // 删除
    @RequestMapping(value = "core/footPrint/delFootPrintById",method = RequestMethod.POST)
    Result delFootPrintById(@RequestBody long footPringtId);

    //查询详情
    @RequestMapping(value = "core/footPrint/selFootPrintById",method = RequestMethod.POST)
    FootPrintDto selFootPrintById(@RequestBody long footPringtId);

}
