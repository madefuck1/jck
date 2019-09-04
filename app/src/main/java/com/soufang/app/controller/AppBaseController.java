package com.soufang.app.controller;

import com.soufang.app.feign.AppShopFeign;
import com.soufang.app.feign.AppUserFeign;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
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

    /**
     * 发送连接
     * @param url
     * @return
     */
    public static String SendGet(String url){
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            //建立实际的连接
            connection.connect();
            //获取所有响应的头字段
            Map<String,List<String>> map = connection.getHeaderFields();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line ;
            while ((line = in.readLine()) != null){
                result += line;
            }
        }catch (Exception e) {
            System.out.println("发送GET请求出现异常"+e);
            e.printStackTrace();
        }
        finally {
            //关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return result;
    }
}
