package com.grab.degree.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.grab.degree.common.redis.RedisConfig;

/**
 * 加载配置
 * @author yjlan
 */
@Configuration
@Import(value = {RedisConfig.class})
public class CommonAutoConfiguration {

}
