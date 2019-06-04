package com.soufang.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.enums.UserLevelEnum;
import com.soufang.base.utils.RedisUtils;
import com.soufang.feign.CommonPullDownFeign;
import com.soufang.feign.PcUserFeign;
import com.soufang.feign.ShopCarFeign;
import com.soufang.vo.BaseVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/3/4
 * @Description: 登录拦截
 */
public class MemberInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    PcUserFeign userFeign;

    @Autowired
    CommonPullDownFeign commonPullDownFeign;

    @Autowired
    ShopCarFeign shopCarFeign;

    private static String url = "";

    //数据加载
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestType = request.getHeader("X-Requested-With");
        //如果requestType能拿到值，并且值为 XMLHttpRequest ,表示客户端的请求为异步请求，那自然是ajax请求了，反之如果为null,则是普通的请求
        Long userId = getUserId(request);
        UserDto userinfo;
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
            // 购物车数量
            if (userId != null) {
                userinfo = userFeign.getUserById(userId);
                int count = shopCarFeign.getShopCarCountByUserId(userId);
                // 是否登录
                response.setHeader("isLonIn", "Y");
                // 用户手机
                response.setHeader("userPhone", trantPhone(userinfo.getPhone()));
                // 购物车数量
                response.setHeader("shopCarCount", count + "");
                // 消息数量 todo 获取消息数量
                response.setHeader("pushInfoCount", "5");
            } else {
                // 是否登录
                response.setHeader("isLonIn", "N");
                response.setHeader("shopCarCount", 0 + "");

            }
        } else {
            if (modelAndView != null) {
                // 分类
                List<AssortDto> list = commonPullDownFeign.getFirstAssort();
                modelAndView.addObject("assortList", list);

                // 是否登录
                if (userId != null) {
                    userinfo = userFeign.getUserById(userId);
                    // 是否登录
                    if (userinfo == null) {
                        modelAndView.addObject("isLonIn", "N");
                    } else {
                        modelAndView.addObject("isLonIn", "Y");
                        // 用户手机
                        modelAndView.addObject("loginPhone", trantPhone(userinfo.getPhone()));
                        // 用户姓名
                        modelAndView.addObject("loginName", userinfo.getUserName());
                        // 用户级别
                        modelAndView.addObject("loginLevel", UserLevelEnum.getByKey(userinfo.getUserLevel()));
                        // 用户头像
                        modelAndView.addObject("loginAvatar", PropertiesParam.file_pre + userinfo.getUserAvatar());
                        // 购物车数量
                        modelAndView.addObject("shopCarCount", shopCarFeign.getShopCarCountByUserId(userId));
                        // 消息数量 todo 获取消息数量
                        modelAndView.addObject("pushInfoCount", 5);
                    }
                } else {
                    modelAndView.addObject("isLonIn", "N");
                    // 用户名
                    modelAndView.addObject("loginPhone", "");
                    // 用户姓名
                    modelAndView.addObject("loginName", "");
                    // 用户级别
                    modelAndView.addObject("loginLevel", "");
                    // 用户头像
                    modelAndView.addObject("loginAvatar", "");
                    // 购物车数量
                    modelAndView.addObject("shopCarCount", 0);
                    // 消息
                    modelAndView.addObject("pushInfoCount", 0);
                }
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

            HandlerMethod myHandlerMethod = (HandlerMethod) handler;
            Method method = myHandlerMethod.getMethod();
            Annotation methodAnnotation = method.getAnnotation(MemberAccess.class);//方法上有该标记
            if (methodAnnotation != null) {
                boolean isLogin = isLogin(request);
                if (isLogin) {
                    return true;
                } else {//未登录
                    String requestType = request.getHeader("X-Requested-With");
                    //如果requestType能拿到值，并且值为 XMLHttpRequest ,表示客户端的请求为异步请求，那自然是ajax请求了，反之如果为null,则是普通的请求
                    if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
                        //Ajax请求返回JSON
                        BaseVo repVo = new BaseVo();
                        repVo.setSuccess(false);
                        repVo.setMessage("请先登录");
                        String data = JSON.toJSONString(repVo);
                        response.setHeader("content-type", "text/html;charset=UTF-8");
                        OutputStream out = response.getOutputStream();
                        out.write(data.getBytes("UTF-8"));
                        return false;
                    } else {
                        String contextPath = request.getContextPath();
                        response.sendRedirect(contextPath + "/user/toLogin");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isLogin(HttpServletRequest request) {
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return false;
        }
        for (Cookie cookie : cookies) {
            switch (cookie.getName()) {
                case "token":
                    token = cookie.getValue();
                    break;
                default:
                    break;
            }
        }
        if (!token.equals("")) {
            String userIdString = RedisUtils.getString(token);
            Long userId;
            if (StringUtils.isNotBlank(userIdString)) {
                userId = Long.valueOf(RedisUtils.getString(token));
            } else {
                userId = null;
            }
            if (userId == null || userFeign.getUserById(userId) == null) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private Long getUserId(HttpServletRequest request) {
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            switch (cookie.getName()) {
                case "token":
                    token = cookie.getValue();
                    break;
                default:
                    break;
            }
        }
        if (!token.equals("")) {
            String userIdString = RedisUtils.getString(token);
            Long userId;
            if (StringUtils.isNotBlank(userIdString)) {
                userId = Long.valueOf(RedisUtils.getString(token));
            } else {
                userId = null;
            }
            if (userId == null || userFeign.getUserById(userId) == null) {
                return null;
            }
            return userId;
        } else {
            return null;
        }
    }

    private String trantPhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
