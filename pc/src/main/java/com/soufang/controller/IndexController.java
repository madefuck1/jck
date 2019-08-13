package com.soufang.controller;

import com.soufang.base.dto.banner.BannerDto;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.jiguang.JMessageDto;
import com.soufang.base.jiguang.JMessageUtils;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.BannerFeign;
import com.soufang.feign.EnquiryFeign;
import com.soufang.feign.EnquiryProductFeign;
import com.soufang.feign.ProductFeign;
import com.soufang.vo.banner.BannerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    EnquiryFeign enquiryFeign;
    @Autowired
    ProductFeign productFeign;
    @Autowired
    BannerFeign bannerFeign;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String toIndex(ModelMap map){
        EnquirySo enquirySo = new EnquirySo();
        enquirySo.setPage(1);
        enquirySo.setLimit(4);
        PageHelp<EnquiryDto> pageHelp = enquiryFeign.getIndexEnquiryList(enquirySo);
        map.put("enquiryDtos",pageHelp.getData());
        PageHelp<ProductDto> productDtos = productFeign.getIndexFootProduct();
        List<ProductDto> list = productDtos.getData();
        //首页Banner图
        List<BannerDto> bannerList = bannerFeign.getList(1000);
        map.put("bannerDtos",bannerList);
        map.put("productDtos1",list);
        map.put("productDtos1",list.subList(0,5));
        map.put("productDtos2",list.subList(5,10));
        map.put("productDtos3",list.subList(10,15));
        return "/index";
    }

    @RequestMapping(value = "index/bannerJump", method = RequestMethod.GET)
    public String BannerJump(@RequestParam("target_type") Integer target_type,@RequestParam("target_id") long target_id){
        if(target_type == 1){
            //跳转到某个店铺详情
            System.out.println(target_id +"店铺");
        }
        if(target_type==2){
            //跳转到某个产品详情
            System.out.println(target_id +"产品");
        }
        return null;
    }


    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "getMessage", method = RequestMethod.GET)
    public JMessageDto getMessage(HttpServletRequest request , Long shopId){
        JMessageDto dto = JMessageUtils.getSinatureByPc();
        dto.setTarget("egt_"+shopId);
        dto.setFrom("egt_"+getUserInfo(request).getUserId());
        return dto ;
    }


    @RequestMapping(value = "index/getMessageDetail/{id}", method = RequestMethod.GET)
    public String  getMessageDetail(@PathVariable long id ,ModelMap map){
        map.put("id",id);
        return "personalCenter/messageDetail";
    }

}
