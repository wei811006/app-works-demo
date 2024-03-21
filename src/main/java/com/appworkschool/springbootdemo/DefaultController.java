package com.appworkschool.springbootdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@RestController
public class DefaultController {

    @Autowired
    TestDataRepository testDataRepository;


    @GetMapping("/test")
    public String index() throws JsonProcessingException {
        log.info("get test");

        Gson gson = new Gson();

        TestData testData = testDataRepository.findFirstByName("test");
        return testData.toString();
    }

    @PostMapping("/test")
    public void test() {
        log.info("post test");
        testDataRepository.save(new TestData("test", "test"));
    }

    @GetMapping("/m5-part3")
    public String m5Part3() {
        return "Test";
    }

    @GetMapping("/random-delay/{ms}")
    public String getRandomDelayedResponse(@PathVariable("ms") int ms) {
        try {
            // 随机生成延迟时间在 300ms 到 800ms 之间
            int delay = new Random().nextInt(ms) + 200;
            Thread.sleep(delay);
            return "Response delayed by " + delay + " milliseconds";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while delaying response";
        }
    }

    @GetMapping("/cpu/{cnt}")
    public String cpu(@PathVariable("cnt") int cnt) {
        fibonacci(cnt); // 计算斐波那契数列以增加 CPU 负载
        return "CPU spike simulated!";
    }

    @GetMapping("/memory/{sizeInMb}")
    public String memory(@PathVariable int sizeInMb) {
        long bytesToAllocate = sizeInMb * 1024L * 1024L; // Convert MB to bytes
        byte[] memoryToConsume = new byte[(int) bytesToAllocate];
        return "Consumed " + sizeInMb + "MB of memory";
    }

    @GetMapping("/status/{rate}")
    public ResponseEntity<String> status(@PathVariable("rate") int rate) {
        System.out.println("/status -> " + rate);
        // 生成一个介于0到99之间的随机数
        int randomNumber = new Random().nextInt(100);
        // 如果随机数小于等于20，返回500错误
        if (randomNumber <= rate) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
        // 否则返回成功响应
        return ResponseEntity.ok("Success");
    }

    // 计算斐波那契数列
    private long fibonacci(int n) {
        System.out.println(n);
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
