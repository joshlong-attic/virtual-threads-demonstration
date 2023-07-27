package com.example.demo;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;

public interface TestContainerConfig {

    GenericContainer<?> httpbin = new GenericContainer<>("mccutchen/go-httpbin")
            .withExposedPorts(8080)
            .waitingFor(new HttpWaitStrategy().forPort(8080));

    @DynamicPropertySource
    static void containerProperties(DynamicPropertyRegistry registry) {
        System.setProperty("httpbin.url",
                "http://" + httpbin.getHost() + ":"
                          + httpbin.getFirstMappedPort().toString());
    }

}



