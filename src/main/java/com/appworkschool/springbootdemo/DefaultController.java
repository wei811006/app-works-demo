package com.appworkschool.springbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DefaultController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TestDataRepository testDataRepository;

    @GetMapping("/test")
    public String index() {
        log.info("get test");
        TestData cacheData = (TestData) redisTemplate.opsForValue().get("testData");
        if (cacheData != null) {
            return cacheData.toString();
        } else {
            TestData testData = testDataRepository.getReferenceById(1L);
            redisTemplate.opsForValue().set("testData", testData);
            return testData.toString();
        }
    }

    @PostMapping("/test")
    public void test() {
        log.info("post test");
        testDataRepository.save(new TestData("test", "test"));
    }

    @DeleteMapping("/test")
    public void delete() {
        log.info("delete test");
        redisTemplate.delete("testData");
    }
}
