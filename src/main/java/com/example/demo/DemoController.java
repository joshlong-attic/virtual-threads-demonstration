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

    @Value("${httpbin.url}")
    private String url;

    private final RestClient restClient;

    DemoController(RestClient.Builder builder) {
        restClient = builder.build();
    }

    @GetMapping("/block")
    public String block() {

        // Make a blocking call (network I/O --> thread-per-request, synchronous operation)
        ResponseEntity<String> result = restClient.get()
                .uri(url + "/delay/3")
                .retrieve()
                .toEntity(String.class);

        logger.info("Got {} on thread {}", result.getStatusCode(), Thread.currentThread());

        return Thread.currentThread() + "\n";
    }

}
