/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FootPrint
 * Author:   小三
 * Date:     2019/5/23 11:34
 * Description: 我的足迹
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.FootPrintFeign;
import com.soufang.app.vo.footPrint.FootPrintVo;
import com.soufang.base.dto.footprint.FootPrintDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.footPrint.FootPrintSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈我的足迹〉
 *
 * @author 小三
 * @create 2019/5/23
 * @since 1.0.0
 */
@Controller
@RequestMapping("app/footPrint/")
public class AppFootPrintController extends AppBaseController{
    @Autowired
    FootPrintFeign footPrintFeign;

    /**
     * 查询展示列表
     * @param request
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public FootPrintVo getList(HttpServletRequest request,@RequestBody FootPrintSo footPrintSo){
        UserDto useInfo =this.getUserInfo(request);
        FootPrintVo footPrintVo = new FootPrintVo();
        FootPrintDto footPrintDto = new FootPrintDto();
        footPrintDto.setUserId(useInfo.getUserId());
        footPrintDto.setLimit(footPrintSo.getLimit());
        footPrintDto.setPage(footPrintSo.getPage());
        PageHelp<FootPrintDto> pageHelp =footPrintFeign.getFootPrintList(footPrintDto);
        footPrintVo.setSuccess(true);
        if(pageHelp.getData() != null && pageHelp.getCount() > 0){
            footPrintVo.setData(pageHelp.getData());
            footPrintVo.setMessage("登录成功");

        }else {
            footPrintVo.setData(null);
            footPrintVo.setMessage("对不起！您没有相关的足迹信息");
        }
        return footPrintVo;
    }
}
