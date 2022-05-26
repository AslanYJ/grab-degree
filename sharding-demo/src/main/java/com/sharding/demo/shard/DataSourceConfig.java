package com.sharding.demo.shard;

import lombok.Data;

/**
 * 数据源
 * @author yjlan
 */
@Data
public class DataSourceConfig {
    /**
     * 主机名
     */
    private String hostName;
    /**
     * 端口号
     */
    private int port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}