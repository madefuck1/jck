/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FootPrintController
 * Author:   小三
 * Date:     2019/5/20 15:43
 * Description: 足迹表
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.footprint.FootPrintDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.footPrint.FootPrintSo;
import com.soufang.service.FootPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈足迹表〉
 *
 * @author 小三
 * @create 2019/5/20
 * @since 1.0.0
 */
@RestController
@RequestMapping("/core/footPrint")
public class FootPrintController {

    @Autowired
    FootPrintService footPrintService;

    /**
     * 查询我的足迹列表
     * @return
     */
    @RequestMapping(value = "getFootPrintList",method = RequestMethod.POST)
    public  PageHelp<FootPrintDto> getFootPrintList(@RequestBody FootPrintSo footPrintSo){
        PageHelp<FootPrintDto> pageHelp = new PageHelp<>();
        List<FootPrintDto> footPrintDtoList=  footPrintService.getFootPrintList(footPrintSo);
        //获取总数量
        int count =footPrintService.getFoPtCount(footPrintSo);
        pageHelp.setData(footPrintDtoList);
        pageHelp.setCount(count);
        return pageHelp;
    }

    /**
     * 查询最近一周足迹
     * @param footPrintSo
     * @return
     */
    @RequestMapping(value = "selFootPrintOneWeek",method = RequestMethod.POST)
    public  PageHelp<FootPrintDto> selFootPrintOneWeek(@RequestBody FootPrintSo footPrintSo){
        PageHelp<FootPrintDto> pageHelp = new PageHelp<>();
        List<FootPrintDto> footPrintDtoList=  footPrintService.selFootPrintOneWeek(footPrintSo);
        //获取总数量
        int count =footPrintDtoList.size();
        pageHelp.setData(footPrintDtoList);
        pageHelp.setCount(count);
        return pageHelp;
    }

    /**
     * 新增足迹
     * @param footPrintDto
     * @return
     */
    @RequestMapping(value = "addFootPrint",method = RequestMethod.POST)
    public boolean addFootPrint(@RequestBody FootPrintDto footPrintDto){
       return footPrintService.insert(footPrintDto);
    }

    /**
     * 删除足迹
     * @param footPringtId
     * @return result
     */
    @RequestMapping(value = "delFootPrintById",method = RequestMethod.POST)
    public Result delFootPrintById(@RequestBody long footPringtId){
        Result result = new Result();
        int j = footPrintService.delFootPrintById(footPringtId);
        if(j> 0){
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else {
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 查询详情
     * @param footPringtId
     * @return
     */
    @RequestMapping(value = "selFootPrintById",method = RequestMethod.POST)
    public FootPrintDto selFootPrintById(@RequestBody String footPringtId){
       return footPrintService.selFootPrintById(footPringtId);
    }

}
