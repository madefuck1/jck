package com.soufang;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * @Auther: chen
 * @Date: 2019/5/5
 * @Description:
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class PcApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PcApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PcApplication.class);
    }
}