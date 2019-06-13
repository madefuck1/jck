package com.soufang.controller;

import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.utils.RedisUtils;
import com.soufang.feign.PcUserFeign;
import com.soufang.feign.ShopFeign;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class BaseController {

    @Autowired
    PcUserFeign pcUserFeign;
    @Autowired
    ShopFeign shopFeign;

    /**
     * 根据header中的Authorization 获取用户信息
     *
     * @param request
     * @return
     */
    public UserDto getUserInfo(HttpServletRequest request) {
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return new UserDto();
        } else {
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "token":
                        token = cookie.getValue();
                        break;
                    default:
                        break;
                }
            }
        }
        String userId = RedisUtils.getString(token);
        if(userId==null){
            return new UserDto();
        }else{
            return pcUserFeign.getUserById(Long.valueOf(userId));
        }

    }

    public ShopDto getShopInfo(HttpServletRequest request){
        UserDto userInfo = getUserInfo(request);
        ShopDto shopInfo = shopFeign.getByUserId(userInfo.getUserId());
        return shopInfo;
    }

    //退出，清除cookie
    public void deletetoken(HttpServletRequest request) {
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return;
        } else {
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "token":
                        token = cookie.getValue();
                        break;
                    default:
                        break;
                }
            }
            boolean info = RedisUtils.existsObject(token);
            if (info) {
                RedisUtils.delkeyObject(token);
            }
        }
    }

    /**
     * 生成随机数 保存token
     *
     * @param userId
     */
    public String setToken(Long userId, HttpServletResponse response) {
        String token = UUID.randomUUID().toString().replace("-", "");
        RedisUtils.setString(token, userId + "", 1296000);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(1296000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return token;
    }
}
