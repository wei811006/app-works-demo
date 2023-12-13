package com.appworkschool.springbootdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
    StringRedisTemplate redisTemplate;

    @Autowired
    TestDataRepository testDataRepository;


    @GetMapping("/test")
    public String index() throws JsonProcessingException {
        log.info("get test");

        Gson gson = new Gson();

        if (redisTemplate.hasKey("testData")) {
            TestData cacheData = gson.fromJson(redisTemplate.opsForValue().get("testData"), TestData.class);
            return cacheData.toString();
        } else {
            TestData testData = testDataRepository.findByName("test");
            redisTemplate.opsForValue().set("testData", gson.toJson(testData));
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
