package com.soufang.app.controller;

import com.soufang.app.feign.AppShopFeign;
import com.soufang.app.feign.AppUserFeign;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AppBaseController {

    @Autowired
    AppUserFeign appUserFeign;

    @Autowired
    AppShopFeign appShopFeign;

    /**
     * 根据header中的Authorization 获取用户信息
     * @param request
     * @return
     */
    public UserDto getUserInfo(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String userId = RedisUtils.getString(token);
        if(StringUtils.isNotBlank(userId)){
            return appUserFeign.detail(Long.valueOf(userId));
        }else {
            return null;
        }
    }

    public ShopDto getShopInfo(HttpServletRequest request){
        UserDto userInfo = getUserInfo(request);
        ShopDto shopInfo = appShopFeign.getByUserId(userInfo.getUserId());
        return shopInfo;
    }
    /**
     * 生成随机数 保存token
     * @param userId
     */
    public void setToken(Long userId){
        String token = UUID.randomUUID().toString().replace("-", "");
        RedisUtils.setString(token,userId+"",1296000);
    }
}
