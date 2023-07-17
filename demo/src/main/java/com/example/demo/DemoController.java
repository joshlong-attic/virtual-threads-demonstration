package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    RestClient restClient = RestClient.create();

    @Value("${httpbinUrl:http://localhost:8087}")
    private String httpbinUrl;

    @GetMapping("/blocking")
    public String delay() {

        ResponseEntity<String> result = restClient.get()
                .uri(httpbinUrl + "/delay/5")
                .retrieve()
                .toEntity(String.class);

        logger.info("Got {} on thread {}", result.getStatusCode(), Thread.currentThread());

        return Thread.currentThread().toString() + "\n";
    }

}
