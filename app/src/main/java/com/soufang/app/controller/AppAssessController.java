package com.soufang.app.controller;


import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppAssessFeign;
import com.soufang.app.vo.AppVo;
import com.soufang.app.vo.assess.AddAssessDetailVo;
import com.soufang.app.vo.assess.AddAssessVo;
import com.soufang.app.vo.assess.AssessVo;
import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        if(assessSo.getLimit() == null){
            assessSo.setLimit(5);
        }
        AssessVo vo = new AssessVo();
        //是否要先判断该用户是买家还是卖家，在去获取列表？暂时是只有卖家可以获取列表
        /*ShopDto shopInfo = this.getShopInfo(request);
        assessSo.setShopId(shopInfo.getShopId());
        if(assessSo.getType() == null || assessSo.getType() == 0 ){
            assessSo.setType(null);
        }*/
        PageHelp<AssessDto> pageHelp = appAssessFeign.getList(assessSo);
        vo.setData(pageHelp.getData());
        vo.setCount(pageHelp.getCount());
        return vo;
    }
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "/putAssess", method = RequestMethod.POST)
    public AppVo toPutAssess(@RequestBody AddAssessVo addAssessVo, HttpServletRequest request){
        AppVo vo = new AppVo();
        UserDto userInfo = this.getUserInfo(request);
        List<AssessDto> assessDtos = new ArrayList<>();
        for (AddAssessDetailVo a: addAssessVo.getData()) {
            AssessDto assessDto = new AssessDto();
            assessDto.setAssessUserId(userInfo.getUserId());
            assessDto.setOrderNumber(addAssessVo.getOrderNumber());
            assessDto.setShopId(addAssessVo.getShopId());
            assessDto.setAssessType(a.getAssessType());
            assessDto.setProductId(a.getProductId());
            assessDto.setAssessContent(a.getAssessContent());
            assessDto.setProductColor(a.getProductColor());
            assessDto.setProductSpec(a.getProductSpec());
            assessDtos.add(assessDto);
        }
        Long orderShopId = appAssessFeign.putAssess(assessDtos);
        if(orderShopId != 0){
            vo.setSuccess(true);
            vo.setMessage("评价成功");
        }else {
            vo.setSuccess(false);
            vo.setMessage("评价失败");
        }
        return vo;
    }

}
