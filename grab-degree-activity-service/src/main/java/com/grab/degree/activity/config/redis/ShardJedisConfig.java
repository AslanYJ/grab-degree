package com.grab.degree.activity.config.redis;


import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Redis分片配置
 * @author yjlan
 */
@ConfigurationProperties(prefix = "grab.degree.jedis")
@Data
public class ShardJedisConfig {
    
    private Integer maxTotal;
    
    private Integer maxIdle;
    
    private Integer minIdle;
    
    private Integer timeOut;
    
    private List<Config> configs;
    
    @Data
    public static class Config {
        
        private String addr;
        
        private String password;
    }
}
