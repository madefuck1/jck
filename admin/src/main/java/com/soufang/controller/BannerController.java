package com.soufang.controller;

import com.soufang.Vo.AdminVo;
import com.soufang.base.Result;
import com.soufang.base.dto.banner.BannerDto;
import com.soufang.base.utils.FtpClient;
import com.soufang.feign.AdminBannerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "admin/banner")
public class BannerController {
    @Autowired
    AdminBannerFeign adminBannerFeign;

    @RequestMapping(value = "addPicture", method = RequestMethod.GET)
    public String addPicture(){
        return "/banner/addPicture";
    }

    @ResponseBody
    @RequestMapping(value = "uploadImg/{terminal}", method = RequestMethod.POST)
    AdminVo uploadImg(@RequestParam("file")MultipartFile file ,@PathVariable Integer terminal){
        Map<String,Object> map = FtpClient.uploadImage(file,"/uploadBanner");
        AdminVo adminVo = new AdminVo();
        BannerDto bannerDto = new BannerDto();
        bannerDto.setBannerImage(map.get("uploadName").toString());
        bannerDto.setTerminal(terminal);
        Result result = adminBannerFeign.addImg(bannerDto);
        if(result.isSuccess()){
            if((boolean)map.get("success")){
                adminVo.setCode("0");
            }else {
                adminVo.setCode("1");
            }
        }
        return adminVo;
    }

    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String toList(ModelMap model){
        List<BannerDto> list = adminBannerFeign.getList(0);
        /*for(int i = 0; i < list.size(); i++){
            list.get(i).getBannerImage().replace(";"," ");
        }*/
        model.put("bannerDto",list);
        return "/banner/banner";
    }

    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public AdminVo deleteById(@PathVariable Long id) {
        Result result = adminBannerFeign.deleteById(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }
}
