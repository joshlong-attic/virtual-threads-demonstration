# demo

This project showcases Spring Boot 3.2 support for Java virtual threads in web applications.
This demo uses Tomcat web server, but the same support is available for Jetty as well.

This application makes a blocking call to an endpoint in an off-the-shelf backend application that delays for 3 seconds before responding.

> Bonus: The demo also showcases two other features new in Spring Boot 3.2:
> [RestClient](com/example/demo/DemoController.java) and 
> [TestContainers for dev](com/example/demo/TestDemoApplication.java)

### Start demo app

The following command will launch both the backend and the demo application
> Note: The backend app is started by Testcontainers as a docker container.
> This requires using a test goal to start the application, as shown in the command below.
```shell
./mvnw clean spring-boot:test-run
```

### Smoke test demo app

Try a single request
```shell
curl http://localhost:8080/block/3
```

The responses above should confirm if you are running with platform threads (e.g. `Thread[#19,http-nio-8080-exec-1,5,main]`) 
or virtual threads (e.g. `VirtualThread[#36,tomcat-handler-0]/runnable@ForkJoinPool-1-worker-1`).

### Load test demo app

Try a number of requests with delays (see options for `hey` to configure load test)
```shell
hey -n 60 -c 20 http://localhost:8080/block/3
```

### Switch thread type

In [application.properties](src/main/resources/application.properties), toggle the value of `spring.threads.virtual.enabled`.

Restart the application and repeat the tests.
Compare the results when using platform vs virtual threads.

### Reduced hardware footprint demo

With virtual threads enabled in [application.properties](src/main/resources/application.properties), edit [TestDemoApplication.java](com/example/demo/TestDemoApplication.java) and set the maxPoolSize to 1.
```java
System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
```

Restart the application and re-run the load test. 
Notice in the log output that the worker thread is always 1.
Notice also the performance is essentially the same as the load test with virtual threads and `maxPoolSize=10`.
This shows that virtual threads can handle concurrent blocking operations more efficiently even with less hardware.
