package com.tangerinespecter.oms;

import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisTemple {

    @Resource
    private RedisHelper redisHelper;

    @Test
    public void testRedis() {
        //redisHelper.set("test1", "hello world");
        //Object test1 = redisHelper.get("test1");
        //System.out.println(test1.toString());
    }
}
