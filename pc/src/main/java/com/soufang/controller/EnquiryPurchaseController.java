package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.FtpClient;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.EnquiryFeign;
import com.soufang.feign.EnquiryProductFeign;
import com.soufang.feign.PurchaseFeign;
import com.soufang.vo.Enquiry.EnquiryAddVo;
import com.soufang.vo.Enquiry.EnquiryUpdateVo;
import com.soufang.vo.Enquiry.EnquiryVo;
import com.soufang.vo.enquiryProduct.EnquiryProductUpdateVo;
import com.soufang.vo.favorite.FavoriteAddVo;
import com.soufang.vo.purchase.PurchseUseRefusedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
            enquiryDto.setEnquiryRemark(request.getParameter("enquiryRemark"));
            enquiryDto.setTakeAddress(request.getParameter("detailStreet"));
            List<EnquiryProductDto> enquiryProductDtos= new ArrayList<>();
            //询价信息
            enquiryProductDto.setProductNumber(Long.parseLong(request.getParameter("productNumber")));
            enquiryProductDto.setProductName(request.getParameter("productName"));
            enquiryProductDto.setProductUnit(request.getParameter("productUnit"));
            enquiryProductDto.setEnquiryNumber(enquiryDto.getEnquiryNumber());
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
     * @param updateVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selEnquiryByNumber",method = RequestMethod.POST)
    public EnquiryDto selEnquiryByNumber(@RequestBody EnquiryUpdateVo updateVo){
        EnquiryDto enquiryDto=  enquiryFeign.selEnquiryByNumber(updateVo.getEnquiryNumber());
        return  enquiryDto;
    }

    //查询产品详情
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selectProductById",method = RequestMethod.POST)
    public EnquiryDto selectProductById(@RequestBody EnquiryProductUpdateVo enquiryProductUpdateVo){
        EnquiryDto enquiryDto=enquiryProductFeign.selectProductById(enquiryProductUpdateVo.getEnquiryProductId());
        return  enquiryDto;
    }

    /**
     * 采用/拒绝
     * @param purchseUseRefusedVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "isUseRefused",method = RequestMethod.POST)
    public Result isUseRefused(@RequestBody PurchseUseRefusedVo purchseUseRefusedVo){
        Result result = purchaseFeign.isUseRefused(purchseUseRefusedVo);
        return result;
    }

    /**
     *  跳转求购详情页面
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
     * 求购列表
     * @param request
     * @param enquiryAddVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "enquiryTableMessage",method = RequestMethod.POST)
    public PageHelp<FavoriteDto> enquiryTableMessage(HttpServletRequest request, @RequestBody EnquiryAddVo enquiryAddVo){
        EnquirySo enquirySo= new EnquirySo();
        //查询参数整理
        enquirySo.setLimit(enquiryAddVo.getLimit());
        enquirySo.setPage(enquiryAddVo.getPage());
        enquirySo.setAssortId(enquiryAddVo.getAssortId());
        //获得返回结果
        PageHelp<FavoriteDto> pageHelp=enquiryFeign.enquiryTableMessage(enquirySo);
        return pageHelp;
    }


    //求购详情-报价
    @RequestMapping(value = "toPurchaseDetail", method = RequestMethod.GET)
    public String toPurchaseDetail() {
        return "enquiry/purchaseDetails";
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
