package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class DemoController {

    @Value("${httpbin.url}")
    private String url;

    private final RestClient restClient;

    DemoController(RestClient.Builder builder) {
        restClient = builder.build();
    }

    @GetMapping("/block/{seconds}")
    public String block(@PathVariable Integer seconds) {

        // Make a blocking call (network I/O --> thread-per-request, synchronous operation)
        ResponseEntity<String> result = restClient.get()
                .uri(url + "/delay/" + seconds)
                .retrieve()
                .toEntity(String.class);

        System.out.println(result.getStatusCode() + " on " + Thread.currentThread());

        return Thread.currentThread() + "\n";
    }

}
