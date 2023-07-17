package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

class TestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(DemoApplication::main)
                .with(TestDemoApplicationConfiguration.class)
                .run(args);
    }

    @ImportTestcontainers(TestContainerConfig.class)
    static class TestDemoApplicationConfiguration {

    }

}
