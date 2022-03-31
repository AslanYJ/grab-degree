package com.grab.degree.activity.config;

import java.util.Objects;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redis分片
 * @author yjlan
 */
@Configuration
@EnableConfigurationProperties(ShardJedisConfig.class)
public class ShardJedisPoolConfiguration {
    
    private final ShardJedisConfig shardJedisConfig;
    
    public ShardJedisPoolConfiguration(ShardJedisConfig shardJedisConfig) {
        if (Objects.isNull(shardJedisConfig.getMaxTotal())) {
            shardJedisConfig.setMaxTotal(8);
        }
        if (Objects.isNull(shardJedisConfig.getMaxIdle())) {
            shardJedisConfig.setMaxIdle(8);
        }
        if (Objects.isNull(shardJedisConfig.getMinIdle())) {
            shardJedisConfig.setMinIdle(0);
        }
        this.shardJedisConfig = shardJedisConfig;
    }


    
    @Bean(destroyMethod = "destroy")
    @ConditionalOnMissingBean
    public ShardJedisManager shardJedisManager() {
        return new ShardJedisManager(shardJedisConfig);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public IShardJedisClient shardJedisClient(ShardJedisManager shardJedisManager) {
        return new ShardJedisClient(shardJedisManager);
    }
}
