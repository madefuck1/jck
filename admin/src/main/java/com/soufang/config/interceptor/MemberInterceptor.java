package com.soufang.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.soufang.Vo.AdminVo;
import com.soufang.base.utils.RedisUtils;
import com.soufang.feign.AdminUserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Auther: chen
 * @Date: 2019/3/4
 * @Description: 登录拦截
 */
public class MemberInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    AdminUserFeign adminUserFeign;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

            HandlerMethod myHandlerMethod = (HandlerMethod) handler;
            Method method= myHandlerMethod.getMethod();
            Annotation methodAnnotation=method.getAnnotation(MemberAccess.class);//方法上有该标记
            if(methodAnnotation != null){
                boolean isLogin = isLogin(request) ;
                if(isLogin){
                    return true;
                }else{//未登录
                    //Ajax请求返回JSON
                    AdminVo repVo = new AdminVo();
                    repVo.setCode("1");
                    repVo.setMsg("请先登录");
                    String data = JSON.toJSONString(repVo);
                    response.setHeader("content-type", "text/html;charset=UTF-8");
                    OutputStream out = response.getOutputStream();
                    out.write(data.getBytes("UTF-8"));
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isLogin(HttpServletRequest request) throws  Exception{
        try {
                String token = request.getHeader("Authorization");
            if( token != null){
                String userIdString = RedisUtils.getString(token);
                Long userId = Long.valueOf(RedisUtils.getString(token));
                if(userId == null || adminUserFeign.getById(userId) == null){
                    return false;
                }
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
