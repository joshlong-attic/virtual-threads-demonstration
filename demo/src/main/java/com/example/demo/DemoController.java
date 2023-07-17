package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    private final RestClient restClient;

    @Value("${httpbinUrl}")
    private String httpbinUrl;

    DemoController(RestClient.Builder builder) {
        restClient = builder.build();
    }

    @GetMapping("/block")
    public String block() {

        return block(3);
    }

    @GetMapping("/block/{seconds}")
    public String block(@PathVariable Integer seconds) {

        // Ignore delays greater than 10 seconds (backend has a hard limit)
        seconds = (seconds > 10) ? 0: seconds;

        ResponseEntity<String> result = restClient.get()
                .uri(httpbinUrl + "/delay/" + seconds)
                .retrieve()
                .toEntity(String.class);

        logger.info("Got {} on thread {}", result.getStatusCode(), Thread.currentThread());

        return Thread.currentThread() + "\n";
    }

}
