/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: AppEnquiryController
 * Author:   小三
 * Date:     2019/5/29 8:58
 * Description: 询盘
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.controller;

/**
 * 〈一句话功能简述〉<br> 
 * 〈询盘〉
 *
 * @author 小三
 * @create 2019/5/29
 * @since 1.0.0
 */

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppEnquiryFeign;
import com.soufang.app.feign.AssortFeign;
import com.soufang.app.vo.enquiry.EnquiryDetailVo;
import com.soufang.app.vo.enquiry.EnquiryGetDetailVo;
import com.soufang.app.vo.enquiry.EnquiryListVo;
import com.soufang.app.vo.enquiry.EnquiryVo;
import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.FtpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("app/enquiry/")
public class AppEnquiryController extends  AppBaseController{

    @Autowired
    AppEnquiryFeign appEnquiryFeign;
    @Autowired
    AssortFeign assortFeign;

    @Value("${upload.enquiry}")
    private String uploadUrl;

    /**
     * 所有询盘列表
     * @param request
     * @param enquirySo
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public EnquiryListVo getList(HttpServletRequest request, @RequestBody EnquirySo enquirySo){
        enquirySo.setEnquiryStatus(3);
        EnquiryListVo enquiryListVo  = new EnquiryListVo();
        PageHelp<EnquiryDto> pageHelp= appEnquiryFeign.getList(enquirySo);
        enquiryListVo.setData(pageHelp.getData());
        enquiryListVo.setSuccess(true);
        enquiryListVo.setMessage("提交审核");
        return enquiryListVo;
    }

    /**
     * 获取询盘编号
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getEnquiryNumber",method = RequestMethod.POST)
    public EnquiryDetailVo getEnquiryNumber(){
        EnquiryDetailVo enquiryDetailVo = new EnquiryDetailVo();
        String enquiryNumber = appEnquiryFeign.toGetEqNumber();
        enquiryDetailVo.setData(enquiryNumber);
        enquiryDetailVo.setSuccess(true);
        enquiryDetailVo.setMessage("提交审核");
        return enquiryDetailVo;
    }

    /**
     * 我的询盘
     * @param enquirySo
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getMyList",method = RequestMethod.POST)
    public EnquiryListVo getMyList(@RequestBody EnquirySo enquirySo ,HttpServletRequest request){
        //获取用户信息
        UserDto userInfo = this.getUserInfo(request);
        enquirySo.setUserId(userInfo.getUserId());
        EnquiryListVo enquiryListVo  =  new  EnquiryListVo();
        PageHelp<EnquiryDto> pageHelp= appEnquiryFeign.getList(enquirySo);
        enquiryListVo.setData(pageHelp.getData());
        enquiryListVo.setSuccess(true);
        enquiryListVo.setMessage("提交审核");
        return enquiryListVo;
    }

    /**
     * 想看询盘详情
     * 需要判断该用户是否能发布报价信息--返回字段信息
     * @param enquiryNumber
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getDetail/{enquiryNumber}",method = RequestMethod.POST)
    public EnquiryGetDetailVo getDetail(@PathVariable String enquiryNumber){
        EnquiryGetDetailVo enquiryGetDetailVo = new EnquiryGetDetailVo();
        EnquiryDto enquiryDto = appEnquiryFeign.selEnquiryByNumber(enquiryNumber);
        enquiryGetDetailVo.setData(enquiryDto);
        enquiryGetDetailVo.setSuccess(true);
        enquiryGetDetailVo.setMessage("提交审核");
        return  enquiryGetDetailVo;
    }

    /**
     * 发布询盘
     * @param file
     * @param request
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "submitEnquiry",method = RequestMethod.POST)
    public EnquiryVo submitEnquiry(MultipartFile[] file,HttpServletRequest request){
        Result result = new Result();
        Map<String,Object> map ;
        UserDto userInfo = this.getUserInfo(request);//
        EnquiryDto enquiryDto =new EnquiryDto();
        EnquiryProductDto enquiryProductDto= new EnquiryProductDto();
        //对数据捕捉异常
        try {
            //询盘信息
            //3为求购数据
            enquiryDto.setStrEnquiryType("1");
            enquiryDto.setEnquiryNumber(request.getParameter("enquiryNumber"));
            enquiryDto.setUserId(userInfo.getUserId());
            enquiryDto.setEnquiryTitle(request.getParameter("enquiryTitle"));
            enquiryDto.setCreateTime(DateUtils.string2Date(request.getParameter("createTime"),DateUtils.format1));
            enquiryDto.setTakeDate(DateUtils.string2Date(request.getParameter("takeDate"),DateUtils.format1));
            enquiryDto.setEndTime(DateUtils.string2Date(request.getParameter("endTime"),DateUtils.format1));
            enquiryDto.setTakeName(request.getParameter("takeName"));
            enquiryDto.setTakePhone(request.getParameter("takePhone"));
            enquiryDto.setEnquiryRemark(request.getParameter("enquiryRemark"));
            enquiryDto.setTakeAddress(request.getParameter("takeAddress"));
            List<EnquiryProductDto> enquiryProductDtos= new ArrayList<>();
            //询价信息
            enquiryProductDto.setProductNumber(Long.parseLong(request.getParameter("productNumber")));
            enquiryProductDto.setProductName(request.getParameter("productName"));
            enquiryProductDto.setProductUnit(request.getParameter("productUnit"));
            //获取product_assort-根据name去获取getAssortIdByName
            enquiryProductDto.setAssortName(request.getParameter("assortName"));
            int str = assortFeign.getAssortIdByName(request.getParameter("assortName")).intValue();
            enquiryProductDto.setProductAssort(Long.parseLong(str+""));
            enquiryProductDto.setEnquiryNumber(enquiryDto.getEnquiryNumber());
            //对文件判断
            if(file != null) {
                String strUrl = "";
                for (int i = 0; i < file.length; i++) {
                    map = FtpClient.uploadImage(file[i], uploadUrl);
                    if ((boolean) map.get("success")) {
                        strUrl += String.valueOf(map.get("uploadName")) + "/";
                        enquiryProductDto.setProductImage(String.valueOf(map.get("uploadName")));
                    } else {
                        result.setSuccess(false);
                        result.setMessage("图片上传失败");
                        //需要提示页面
                    }
                }
            }
            enquiryProductDtos.add(enquiryProductDto);
            enquiryDto.setEnquiryProductDto(enquiryProductDtos);
        }catch (Exception e){
            e.printStackTrace();
        }
        result= appEnquiryFeign.addEnquiry(enquiryDto);
        return judgeEnquiryVo(result);
    }
    private EnquiryVo judgeEnquiryVo(Result result){
        EnquiryVo enquiryVo = new EnquiryVo();
        if(result.isSuccess()){
            enquiryVo.setMessage(result.getMessage());
            enquiryVo.setSuccess(result.isSuccess());
        }else {
            enquiryVo.setSuccess(result.isSuccess());
            enquiryVo.setMessage(result.getMessage());
        }
        return enquiryVo;
    }

}
