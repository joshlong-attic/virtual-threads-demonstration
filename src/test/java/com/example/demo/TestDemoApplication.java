package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

class TestDemoApplication {

    static {
        // Max carrier threads pool size: default is 256
        // Default parallelism is max(available processors, maxPoolSize)
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "10");
    }

    public static void main(String[] args) {
        SpringApplication.from(DemoApplication::main)
                .with(TestDemoApplicationConfiguration.class)
                .run(args);
    }

    @ImportTestcontainers(TestContainerConfig.class)
    static class TestDemoApplicationConfiguration {

    }

}
