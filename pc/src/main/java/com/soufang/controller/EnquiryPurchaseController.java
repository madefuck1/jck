package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.FtpClient;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.AssortFeign;
import com.soufang.feign.EnquiryFeign;
import com.soufang.feign.EnquiryProductFeign;
import com.soufang.feign.PurchaseFeign;
import com.soufang.vo.Enquiry.EnquiryAddVo;
import com.soufang.vo.Enquiry.EnquiryDetailsVo;
import com.soufang.vo.Enquiry.EnquiryUpdateVo;
import com.soufang.vo.Enquiry.EnquiryVo;
import com.soufang.vo.enquiryProduct.EnquiryProductUpdateVo;
import com.soufang.vo.favorite.FavoriteAddVo;
import com.soufang.vo.purchase.PurchseUseRefusedVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/5/29
 * @Description:
 */
@Controller
@RequestMapping("enquiry")
public class EnquiryPurchaseController extends BaseController{

    @Autowired
    EnquiryFeign enquiryFeign;
    @Autowired
    EnquiryProductFeign enquiryProductFeign ;
    @Autowired
    PurchaseFeign purchaseFeign;
    @Autowired
    AssortFeign assortFeign ;


    @Value("${upload.enquiry}")
    private String uploadUrl;


    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(String enquiryNumber,String isUpdate,ModelMap modelMap){
        if(isUpdate==null || "".equals(isUpdate)){
            //新增
            String newEnquiryNumber = enquiryFeign.toGetEqNumber();
            modelMap.put("enquiryNumber",newEnquiryNumber);
            modelMap.put("isUpdate","");
        }else{
            //修改
            modelMap.put("enquiryNumber",enquiryNumber);
            modelMap.put("isUpdate",isUpdate);
        }
        return "/personalCenter/createEnquiry";
    }

    /**
     * 新增
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "createEnquiry",method = RequestMethod.POST)
    public EnquiryVo createEnquiry(MultipartFile file, HttpServletRequest request){
        Result result = new Result();
        Map<String,Object> map ;
        UserDto userInfo = this.getUserInfo(request);//
        EnquiryDto enquiryDto =new EnquiryDto();
        EnquiryProductDto enquiryProductDto= new EnquiryProductDto();
        //对数据捕捉异常
        try {
            //询盘信息
            enquiryDto.setStrEnquiryType("0");
            enquiryDto.setEnquiryNumber(request.getParameter("enquiryNumber"));
            enquiryDto.setUserId(userInfo.getUserId());
            enquiryDto.setEnquiryTitle(request.getParameter("enquiryTitle"));
            enquiryDto.setCreateTime(DateUtils.string2Date(request.getParameter("createTime"),DateUtils.format1));
            enquiryDto.setTakeDate(DateUtils.string2Date(request.getParameter("takeDate"),DateUtils.format1));
            enquiryDto.setEndTime(DateUtils.string2Date(request.getParameter("endTime"),DateUtils.format1));
            enquiryDto.setEnquiryRemark(request.getParameter("enquiryRemark"));
            enquiryDto.setTakeAddress(request.getParameter("detailStreet"));
            List<EnquiryProductDto> enquiryProductDtos= new ArrayList<>();
            //询价信息
            enquiryProductDto.setProductNumber(Long.parseLong(request.getParameter("productNumber")));
            enquiryProductDto.setProductName(request.getParameter("productName"));
            enquiryProductDto.setProductUnit(request.getParameter("productUnit"));
            enquiryProductDto.setEnquiryNumber(enquiryDto.getEnquiryNumber());
            enquiryProductDto.setProductAssort(Long.parseLong(request.getParameter("productAssort")));
            enquiryProductDtos.add(enquiryProductDto);
            enquiryDto.setEnquiryProductDto(enquiryProductDtos);
        map = FtpClient.uploadImage(file,uploadUrl);
        if((boolean)map.get("success")){
            enquiryProductDto.setProductImage(String.valueOf(map.get("uploadName")));
            result= enquiryFeign.addEnquiry(enquiryDto);
        } else {
            result.setSuccess(false);
            result.setMessage("图片上传失败");
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return judgeEnquiry(result);
    }

    /**
     * 修改信息
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "updateEnquiryAndProduct",method = RequestMethod.POST)
    public EnquiryVo updateEnquiryAndProduct(MultipartFile file, HttpServletRequest request){
        Result result= new Result();
        Map<String,Object> map ;
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        EnquiryDto enquiryDto =new EnquiryDto();
        enquiryDto.setUserId(userInfo.getUserId());
        EnquiryProductDto enquiryProductDto= new EnquiryProductDto();
        //对数据捕捉异常
        try {
            //询盘信息
            enquiryDto.setEnquiryNumber(request.getParameter("enquiryNumber"));
            enquiryDto.setUserId(userInfo.getUserId());
            enquiryDto.setEnquiryTitle(request.getParameter("enquiryTitle"));
            enquiryDto.setCreateTime(DateUtils.string2Date(request.getParameter("createTime"),DateUtils.format1));
            enquiryDto.setTakeDate(DateUtils.string2Date(request.getParameter("takeDate"),DateUtils.format1));
            enquiryDto.setEnquiryRemark(request.getParameter("enquiryRemark"));
            enquiryDto.setTakeAddress(request.getParameter("detailStreet"));
            List<EnquiryProductDto> enquiryProductDtos= new ArrayList<>();
            //询价信息
            enquiryProductDto.setProductNumber(Long.parseLong(request.getParameter("productNumber")));
            enquiryProductDto.setProductName(request.getParameter("productName"));
            enquiryProductDto.setProductUnit(request.getParameter("productUnit"));
            enquiryProductDto.setEnquiryNumber(enquiryDto.getEnquiryNumber());
            enquiryProductDto.setEnquiryProductId(Long.parseLong(request.getParameter("enquiryProductId")));
            enquiryProductDtos.add(enquiryProductDto);
            enquiryDto.setEnquiryProductDto(enquiryProductDtos);
        }catch (Exception e){
            e.printStackTrace();
        }

        //对文件判断
        if(file != null){
            map = FtpClient.uploadImage(file,uploadUrl);
            if((boolean)map.get("success")){
                enquiryProductDto.setProductImage(String.valueOf(map.get("uploadName")));
                result= enquiryFeign.updateEnquiryAndProduct(enquiryDto);
            } else {
                result.setSuccess(false);
                result.setMessage("图片上传失败");
            }
        }else {
            result=enquiryFeign.updateEnquiryAndProduct(enquiryDto);
        }
        return judgeEnquiry(result);
    }

    /**
     * 查询上级信息
     * @param enquiryProductUpdateVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selParentIdByAssortId",method = RequestMethod.GET)
    public Long selParentIdByAssortId(@RequestBody EnquiryProductUpdateVo enquiryProductUpdateVo){
      return  assortFeign.selParentIdByAssortId(enquiryProductUpdateVo.getProductAssort());
    }

    /**
     * 删除照片
     * @param enquiryProductUpdateVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "delEnProImgUrl",method = RequestMethod.POST)
    public Result delEnProImgUrl(@RequestBody EnquiryProductUpdateVo enquiryProductUpdateVo){
        Result result = enquiryProductFeign.delEnProImgUrl(enquiryProductUpdateVo);
        return  result;
    }


    /**
     * 查看具体详情-询盘
     * @param enquiryDetailsVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selEnquiryByNumber",method = RequestMethod.POST)
    public EnquiryDto selEnquiryByNumber(@RequestBody EnquiryDetailsVo enquiryDetailsVo){
        EnquirySo enquirySo = new EnquirySo();
        enquirySo.setEnquiryNumber(enquiryDetailsVo.getEnquiryNumber());
        enquirySo.setEnquiryProductId(enquiryDetailsVo.getEnquiryProductId());
        EnquiryDto enquiryDto=  enquiryFeign.selEnquiryByNumber(enquirySo);
        return  enquiryDto;
    }

    /**
     * 采用/拒绝
     * @param purchseUseRefusedVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "acceptPurchase",method = RequestMethod.POST)
    public Result acceptPurchase(@RequestBody PurchseUseRefusedVo purchseUseRefusedVo){
        Result result= new Result();
        PurchaseSo purchaseSo =new PurchaseSo();
        purchaseSo.setOfferStatus(purchseUseRefusedVo.getOfferStatus());
        purchaseSo.setPurchaseNumber(purchseUseRefusedVo.getPurchaseNumber());
        purchaseSo.setEnquiryNumber(purchseUseRefusedVo.getEnquiryNumber());
        int i = purchaseFeign.acceptPurchasePc(purchaseSo);
        if(i>0){
            result.setMessage("报价成功");
        }else{
            result.setMessage("报价异常");
        }
        return result;
    }

    /**
     *  跳转求购详情页面
     * @param enquiryProductId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "toPurchaseDetails", method = RequestMethod.GET)
    public String toPurchaseDetails(String enquiryProductId,ModelMap modelMap){
        modelMap.put("enquiryProductId",enquiryProductId);
        return "enquiry/enquiryDetails";
    }

    /**
     *  跳转询盘详情页面
     * @param enquiryProductId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "toEnquiryDetails", method = RequestMethod.GET)
    public String toEnquiryDetails(String enquiryProductId,ModelMap modelMap){
        modelMap.put("enquiryProductId",enquiryProductId);
        return "personalCenter/enquiryDetails";
    }

    //求购列表
    @RequestMapping(value = "toEnquiryList", method = RequestMethod.GET)
    public String toEnquiryList() {
        return "enquiry/enquityList";
    }

    /**
     * 列表-求购
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "getEnquiryList",method = RequestMethod.POST)
    public  PageHelp<EnquiryDto> getEnquiryList(HttpServletRequest request,@RequestBody EnquiryAddVo enquiryAddVo){
        EnquirySo enquirySo =new EnquirySo();
        BeanUtils.copyProperties(enquiryAddVo,enquirySo);
        PageHelp<EnquiryDto> pageHelp= enquiryFeign.getList(enquirySo);
        return pageHelp;
    }

    /**
     * 获取分类信息
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "getIdName",method = RequestMethod.POST)
    public List<Map<String, Object>> getIdName(){
        List<Map<String, Object>> list = assortFeign.getIdName();
        return list;
    }


    /**
     * 判断是否为商家
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "isOrNoShopUser", method = RequestMethod.POST)
    public Result isOrNoShopUser(HttpServletRequest request,@RequestBody EnquirySo enquirySo ){
        Result result= new Result();
        UserDto userInfo = getUserInfo(request);
        //查询当前商铺信息
        ShopDto shopInfo = shopFeign.getByUserId(userInfo.getUserId());
        if("".equals(shopInfo.getShopStatus())|| null==shopInfo.getShopStatus()) {
            result.setMessage("不可以报价");
        }else{
        if(shopInfo.getShopStatus()==0){
            //是商家 且询盘产品不是自己
            //查询当前产品所属询盘用户信息
            Long userId=enquiryFeign.selUserIdByEnquiryNumber(enquirySo.getEnquiryNumber());
            if(!(userInfo.getUserId().equals(userId))){
                //相等则是统一商家
                result.setMessage("可以报价");
            }
        }else{
            result.setMessage("不可以报价");
        }
        }
        return result;
    }

    /**
     * 发布报价
     * @param purchaseDto
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "purchase", method = RequestMethod.POST)
    public Result purchase(@RequestBody PurchaseDto purchaseDto,HttpServletRequest request){
        UserDto userDto=this.getUserInfo(request);
        purchaseDto.setUserId(userDto.getUserId());
        Result result= new Result();
       if(enquiryFeign.purchase(purchaseDto)>0){
           result.setSuccess(true);
           result.setMessage("报价成功");
       }else {
           result.setSuccess(false);
           result.setMessage("报价异常，请联系服务人员");
       }
       return result;
    }


    //返回参数设置
    private EnquiryVo judgeEnquiry(Result result){
        EnquiryVo vo = new EnquiryVo();
        if(result.isSuccess()){
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        }else {
            vo.setMessage(result.getMessage());
            vo.setSuccess(result.isSuccess());
        }
        return vo;
    }
}
