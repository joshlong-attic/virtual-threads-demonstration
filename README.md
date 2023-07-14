# demo

This project illustrates the use of Java virtual threads using a Spring Boot web application

### Start the backend application

```shell
docker stop httpbin
docker run -d --rm -p 8087:80 --name httpbin arnaudlacour/httpbin
```

### Build and publish the demo application

Start a local registry
```shell
docker stop registry
docker run -d --rm -p 5001:5000 --name registry registry:2
```

Build and publish application image
> Note: [kbld](https://carvel.dev/kbld/docs/latest/install) orchestrates the build, tag, and push using docker as the default mechanism.
> If you prefer to use the docker CLI directly, reverse the commenting below.
```shell
kbld -f kbld.yaml
#docker build . --tag localhost:5001/demo-app && docker push localhost:5001/demo-app
```

Verify that image is on the registry
```shell
skopeo list-tags docker://localhost:5001/demo-app  --tls-verify=false
```

### Configure runtime options

Set docker options (or [other options](https://docs.docker.com/engine/reference/run/#runtime-constraints-on-resources))
```shell
DOCKER_MEMORY=--memory='512mb'
DOCKER_MEMORY_SWAP=--memory-swap='512mb'
DOCKER_CPUS=--cpus='.5'
echo "DOCKER OPTIONS: $DOCKER_MEMORY $DOCKER_MEMORY_SWAP $DOCKER_CPUS"
```
_OR..._

Use all default options
```shell
unset DOCKER_MEMORY DOCKER_MEMORY_SWAP DOCKER_CPUS
echo "DOCKER OPTIONS: $DOCKER_MEMORY $DOCKER_MEMORY_SWAP $DOCKER_CPUS"
```

### Test the demo application

Using virtual threads...
```shell
docker stop demo-app
echo "DOCKER OPTIONS: $DOCKER_MEMORY $DOCKER_MEMORY_SWAP $DOCKER_CPUS"
docker run --rm -p 8088:8080 $DOCKER_MEMORY $DOCKER_MEMORY_SWAP $DOCKER_CPUS --env SPRING_THREADS_VIRTUAL_ENABLED='true' --name demo-app localhost:5001/demo-app
```
_OR..._

Using platform threads...
```shell
docker stop demo-app 
echo "DOCKER OPTIONS: $DOCKER_MEMORY $DOCKER_MEMORY_SWAP $DOCKER_CPUS"
docker run --rm -p 8088:8080 $DOCKER_MEMORY $DOCKER_MEMORY_SWAP $DOCKER_CPUS --env SPRING_THREADS_VIRTUAL_ENABLED='false' --name demo-app localhost:5001/demo-app
```

### Smoke test the application

Try a single request that does not make a network call
```shell
curl http://localhost:8088
```

Try a single request that makes a call to the backend
```shell
curl http://localhost:8088/delay
```

The responses above should confirm if you are running with platform threads (e.g. `Thread[#19,http-nio-8080-exec-1,5,main]`) or virtual threads (e.g. `VirtualThread[#36,tomcat-handler-0]/runnable@ForkJoinPool-1-worker-1`). 

### Test the demo application

In a separate terminal, use [docker stats](https://docs.docker.com/engine/reference/commandline/stats) to monitor container resource utilization
```shell
docker stats
```

Try a number of requests with delays (see options for `hey` to configure load test)
```shell
printf "Thread type: " && curl http://localhost:8088
time hey -n 200 -c 50 http://localhost:8088/delay
```
