package com.sharding.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yjlan
 * @version V1.0
 * @Description (这里用一句话描述这个类的作用)
 * @date 2022.05.26 20:16
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ShardingDemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ShardingDemoApplication.class, args);
    }
}
