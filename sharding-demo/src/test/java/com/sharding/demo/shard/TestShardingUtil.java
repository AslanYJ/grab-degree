package com.sharding.demo.shard;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sharding.demo.ShardingDemoApplication;

/**
 * @author yjlan
 * @version V1.0
 * @Description (这里用一句话描述这个类的作用)
 * @date 2022.05.26 20:27
 */
@SpringBootTest(classes = ShardingDemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestShardingUtil {

    @Resource
    private ShardingUtil shardingUtil;
    
    @Test
    public void testInit() {
        shardingUtil.initTables("/sql/init.sql");
    }
}
