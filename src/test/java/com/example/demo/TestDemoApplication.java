package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;

class TestDemoApplication {

    public static void main(String[] args) throws Exception {
        var port = 8080;
        var httpbin = new GenericContainer<>("mccutchen/go-httpbin")
                .withAccessToHost(true)
                .withExposedPorts(port)
                .waitingFor(new WaitAllStrategy());
        httpbin.start();

        var threads = Integer.toString(Runtime.getRuntime().availableProcessors());
        System.setProperty("server.tomcat.threads.max", threads);
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", threads);

        var url = "http://" + httpbin.getHost() + ":" + httpbin.getMappedPort(port);
        System.setProperty("httpbin.url", url);

        SpringApplication.run(DemoApplication.class);
    }
}
