package com.appworkschool.springbootdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DefaultController {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    TestDataRepository testDataRepository;


    @GetMapping("/test")
    public String index() throws JsonProcessingException {
        log.info("get test");

        ObjectMapper mapper = new ObjectMapper();
        if (redisTemplate.hasKey("testData")){

            JSONObject cacheData = mapper.readValue(redisTemplate.opsForValue().get("testData"), JSONObject.class);
            return cacheData.toString();
        } else {
            TestData testData = testDataRepository.getReferenceById(1L);
            redisTemplate.opsForValue().set("testData", mapper.writeValueAsString(testData));
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
