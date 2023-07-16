# demo

This project showcases Spring Boot's support for Java virtual threads in web applications.
This demo uses Tomcat web server, but the same support is available for Jetty as well.

This application makes a blocking call to an endpoint in an off-the-shelf backend application that delays for 5 seconds before responding.

### Start demo app

The following command will launch both the backend and the demo application
> Note: The backend app is started by Testcontainers as a docker container.
> This requires using a test goal to start the application, as shown in the command below.
```shell
./mvnw clean spring-boot:test-run
```

### Smoke test demo app

Try a single request that does not make a network call
> Note: Make sure the port number matches the desired startup command above
```shell
curl http://localhost:8081
```

Try a single request that makes a call to the backend
> Note: Make sure the port number matches the desired startup command above
```shell
curl http://localhost:8081/blocking
```

The responses above should confirm if you are running with platform threads (e.g. `Thread[#19,http-nio-8081-exec-1,5,main]`) or virtual threads (e.g. `VirtualThread[#36,tomcat-handler-0]/runnable@ForkJoinPool-1-worker-1`).

### Test demo app

Try a number of requests with delays (see options for `hey` to configure load test)
```shell
hey -n 1 -c 1 http://localhost:8081/blocking
```
