package com.example.demo;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;

public interface TestContainerConfig {

    GenericContainer<?> httpbin = new GenericContainer<>("mccutchen/go-httpbin")
            .withExposedPorts(8080)
            .waitingFor(new HttpWaitStrategy().forPort(8080))
            .withReuse(false); // optional

    @DynamicPropertySource
    static void containerProperties(DynamicPropertyRegistry registry) {
        System.setProperty("httpbinUrl", "http://"
                + httpbin.getHost()
                + ":"
                + httpbin.getFirstMappedPort().toString());
    }

}
