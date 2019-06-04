package com.soufang.controller;


import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.utils.FtpClient;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.vo.BaseVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 店铺装修
 */
@Controller
@RequestMapping(value = "/store")
public class StoreConstructionController extends BaseController {
    @Value("${upload.uploadLogoPic}")
    private String uploadUrl;//店铺装修logo照片上传地址

    @RequestMapping(value = "logoPicUpload", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public BaseVo logoPicUpload(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        BaseVo vo = new BaseVo();
        ShopDto shopInfo = getShopInfo(request);
        Map<String,Object> map = FtpClient.uploadImage(file,uploadUrl);

//        if((boolean)map.get("success")){
//            companyDto.setCompUrls(String.valueOf(map.get("uploadName")));
//            result = pcUserFeign.addCompany(companyDto);
//        }else {
//            baseVo.setSuccess(false);
//            baseVo.setMessage("营业执照上传失败");
//            return baseVo;
//        }

        return vo;
    }

}
