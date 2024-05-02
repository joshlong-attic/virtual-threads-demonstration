#!/usr/bin/env bash
#hey  -c 20 -n 60 http://localhost:9090/block/3
#curl -c 20 -n 60 http://localhost:9090/block/3

threads(){
  curl http://localhost:9090/actuator/metrics/jvm.threads.live |  jq -r '.measurements[].value'
}

T1=`threads`
oha -c 20 -n 60 http://localhost:9090/block/3
T2=`threads`
echo $T1 - $T2