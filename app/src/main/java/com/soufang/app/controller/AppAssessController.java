package com.soufang.app.controller;


import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppAssessFeign;
import com.soufang.app.vo.assess.AssessVo;
import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("app/assess/")
public class AppAssessController extends AppBaseController{

    @Autowired
    AppAssessFeign appAssessFeign;

    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public AssessVo getList(HttpServletRequest request,@RequestBody AssessSo assessSo ){
        AssessVo vo = new AssessVo();
        ShopDto shopInfo = this.getShopInfo(request);
        assessSo.setShopId(shopInfo.getShopId());
        if(assessSo.getType() == 0 ){
            assessSo.setType(null);
        }
        PageHelp<AssessDto> pageHelp = appAssessFeign.getList(assessSo);
        vo.setData(pageHelp.getData());
        vo.setCount(pageHelp.getCount());
        return vo;
    }


}
