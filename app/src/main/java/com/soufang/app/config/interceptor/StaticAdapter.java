package com.soufang.app.config.interceptor;

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
    public AppMemberInterceptor getMemberInterceptor() {
        return new AppMemberInterceptor();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMemberInterceptor());
        super.addInterceptors(registry);
    }
}
