/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: AppPurchaseController
 * Author:   小三
 * Date:     2019/5/29 11:17
 * Description: 询价产品信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppEnquiryFeign;
import com.soufang.app.feign.AppPurchaseFeign;
import com.soufang.app.vo.enquiry.EnquiryVo;
import com.soufang.app.vo.purchase.AcceptPurchaseVo;
import com.soufang.app.vo.purchase.PurchaseVo;
import com.soufang.app.vo.purchase.addPurchaseVo;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.search.purchase.PurchaseSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈询价产品信息〉
 *
 * @author 小三
 * @create 2019/5/29
 * @since 1.0.0
 */
@Controller
@RequestMapping("app/enquiry/")
public class AppPurchaseController extends AppBaseController{
    @Autowired
    AppPurchaseFeign appPurchaseFeign;
    @Autowired
    AppEnquiryFeign appEnquiryFeign;

    /**
     * 查询所有报价信息
     * @param enquirySo
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getPurchaseList",method = RequestMethod.POST)
    public EnquiryVo getPurchaseList(@RequestBody EnquirySo enquirySo, HttpServletRequest request){
        UserDto userDto=this.getUserInfo(request);
        //卖家要通过SHOPID查询报价信息
        enquirySo.setUserId(userDto.getUserId());
        //因为用户信息的商铺信息取不到
        enquirySo.setShopId(userDto.getUserId());
        PageHelp<EnquiryDto> pageHelps =appEnquiryFeign.getList(enquirySo);
        EnquiryVo enquiryVo = new EnquiryVo();
        enquiryVo.setData(pageHelps.getData());
        enquiryVo.setSuccess(true);
        enquiryVo.setMessage("提交审核");
        return enquiryVo;
    }

    /**
     * 查询报价信息
     * @param enquiryNumber
     * @return
     */
   @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getPurchaseList/{enquiryNumber}",method = RequestMethod.POST)
    public PurchaseVo getPurchaseList(@PathVariable String enquiryNumber ){
        PurchaseVo purchaseVo= new PurchaseVo();
        EnquirySo enquirySo= new EnquirySo();
        enquirySo.setEnquiryNumber(enquiryNumber);
       EnquiryDto enquiryDto = appEnquiryFeign.selEnquiryByNumber(enquirySo);
        List<PurchaseDto> purchaseDtos =enquiryDto.getEnquiryProductDto().get(0).getPurchases();
        purchaseVo.setData(purchaseDtos);
        purchaseVo.setMessage("提交审核");
        purchaseVo.setSuccess(true);
        return purchaseVo;
    }

    /**
     * 6.8接收报价-拒绝
     * @param purchaseSo
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "acceptPurchase",method = RequestMethod.POST)
    public AcceptPurchaseVo acceptPurchase(@RequestBody PurchaseSo purchaseSo){
        AcceptPurchaseVo acceptPurchaseVo= new AcceptPurchaseVo();
        //接收
        purchaseSo.setOfferStatus(2);
        EnquiryDto enquiryDto = appPurchaseFeign.acceptPurchase(purchaseSo);
        if(enquiryDto.getEnquiryNumber()!=null){
            //接受成功
            acceptPurchaseVo.setEnquiryStatus(enquiryDto.getEnquiryStatus().toString());
            acceptPurchaseVo.setStatusMessage(enquiryDto.getStatusMessage());
            acceptPurchaseVo.setMessage("提交审核");
            acceptPurchaseVo.setSuccess(true);
        }else{
            //失败
            acceptPurchaseVo.setMessage("提交审核");
            acceptPurchaseVo.setSuccess(false);
        }
        return acceptPurchaseVo;
    }

    //6.9报价
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "purchase",method = RequestMethod.POST)
    public AcceptPurchaseVo purchase(@RequestBody addPurchaseVo addPurchaseVo, HttpServletRequest request){
        //获取用户信息
        UserDto userDto=this.getUserInfo(request);
        AcceptPurchaseVo acceptPurchaseVo= new AcceptPurchaseVo();
        PurchaseDto purchaseDto = new PurchaseDto();
        //参数整理
        purchaseDto.setEnquiryNumber(addPurchaseVo.getEnquiryNumber());
        purchaseDto.setRemark(addPurchaseVo.getRemark());
        purchaseDto.setUnitPrice(new BigDecimal(addPurchaseVo.getUnitPrice()));
        purchaseDto.setUserId(userDto.getUserId());
        //调用报价方法
        int i =appPurchaseFeign.purchase(purchaseDto);
        if(i>0){
            acceptPurchaseVo.setMessage("提交审核");
            acceptPurchaseVo.setSuccess(true);
        }else{
            acceptPurchaseVo.setMessage("提交审核");
            acceptPurchaseVo.setSuccess(false);
        }
        return acceptPurchaseVo;
    }


}
