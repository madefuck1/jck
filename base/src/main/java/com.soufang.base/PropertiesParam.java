package com.soufang.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: chen
 * @Date: 2019/5/15
 * @Description:
 */
@Component
@PropertySource(value = "classpath:conf.properties")
public class PropertiesParam {

    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.returnPort}")
    private int returnPort;


    public static String file_pre ;

    @PostConstruct
    public void getApiToken() {
        file_pre = "http://"+ftpHost+":"+returnPort+"/";
    }




}
