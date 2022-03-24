package com.grab.degree.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SpringBoot启动
 *
 * @author yjlan
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CmsServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CmsServiceApplication.class, args);
    }
    
}
