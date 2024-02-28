package com.yupi.springbootinit.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisLimiterMangerTest {
    @Resource
    private RedisLimiterManger redisLimiterManger;
    @Test
    void doRateLimiter() throws InterruptedException {
        String userId="1";
        for (int i=0;i<2;i++){
            redisLimiterManger.doRateLimit(userId);
            System.out.println("成功");
        }
        Thread.sleep(1000);
        for (int i=0;i<5;i++){
            redisLimiterManger.doRateLimit(userId);
            System.out.println("成功");
        }
    }
}