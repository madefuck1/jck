package com.soufang.config;

import com.soufang.config.interceptor.MemberInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *拦截器注入
 */
@Configuration
public class StaticAdapter extends WebMvcConfigurerAdapter {

    @Bean
    public MemberInterceptor getMemberInterceptor() {
        return new MemberInterceptor();
    }

    //访问过滤静态文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMemberInterceptor());
        super.addInterceptors(registry);
    }
}
