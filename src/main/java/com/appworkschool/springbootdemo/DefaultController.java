package com.appworkschool.springbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DefaultController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TestDataRepository testDataRepository;

    @GetMapping("/test")
    public String index() {
        log.info("get test");
        Object cacheData = stringRedisTemplate.opsForHash().get("test", "test");
        if (cacheData != null) {
            return ((TestData)cacheData).toString();
        } else {
            TestData testData = testDataRepository.getReferenceById(1L);
            stringRedisTemplate.opsForHash().putIfAbsent("test", "test", testData);
            return testData.toString();
        }
    }

    @PostMapping("/test")
    public void test() {
        log.info("post test");
        testDataRepository.save(new TestData("test", "test"));
    }
}
