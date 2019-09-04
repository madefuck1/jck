package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.banner.BannerDto;
import com.soufang.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    /**
     * 添加banner信息
     * @param bannerDto
     * @return
     */
    @RequestMapping(value = "addImg",method = RequestMethod.POST)
    Result addImg(@RequestBody BannerDto bannerDto){
        Result result = new Result();
        int i = bannerService.addImg(bannerDto);
        if(i > 0 ){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 得到所有banner
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    List<BannerDto> getList(@RequestBody Integer terminal){
        return bannerService.getList(terminal);
    }

    /**
     * 删除banner
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteById",method = RequestMethod.POST)
    Result deleteById(@RequestBody Long id){
        Result result = new Result();
        if(bannerService.deleteById(id) > 0){
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else {
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    //获取首页类别大图
    @RequestMapping(value = "getAssortPicture",method = RequestMethod.POST)
    List<BannerDto> getAssortPicture(@RequestBody BannerDto bannerDto){
        List<BannerDto> assortpictures = bannerService.getAssortPicture(bannerDto);
        return assortpictures;
    }
}
