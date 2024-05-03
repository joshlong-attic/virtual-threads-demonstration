#!/usr/bin/env bash
#hey  -c 20 -n 60 http://localhost:9090/block/3
#curl -c 20 -n 60 http://localhost:9090/block/3

threads(){
  echo "virtual threads enabled: `curl http://localhost:9090/vt` "
  echo "live threads : `curl http://localhost:9090/actuator/metrics/jvm.threads.live |  jq -r '.measurements[].value' ` "
}

oha -c 20 -n 60 --no-tui  http://localhost:9090/block/3
threads