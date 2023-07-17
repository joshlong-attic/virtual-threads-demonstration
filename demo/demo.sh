#_ECHO_OFF

# To run:
#   Download: https://github.com/mgbrodi/demorunner
#   <PATH_TO_DEMORUNNER>/demorunner.sh demo.sh

echo DEMO_HOME=$(pwd)

# Set iTerm badge
echo -e "\033]50;SetProfile=Demo\a"
iterm2_set_badge "platform threads"

# Clean docker
# docker ps -a -q | xargs -n1 docker stop; docker system prune -af; docker volume prune -f; docker network prune -f

clear
#_ECHO_ON

#_ECHO_# Highlights: Java 21 & Spring Boot 3.2. + Bonus! RestClient, TestContainers for dev
idea .
# Set Tomcat threads to 50
# run with: ./mvnw clean spring-boot:test-run
docker ps
curl http://localhost:8080/blocking    # Smoke test
hey -n 300 -c 100 http://localhost:8080/blocking    # Load test

# What if we constrain thread resources?
# Set Tomcat threads to 25
curl http://localhost:8080/blocking    # Smoke test
hey -n 300 -c 100 http://localhost:8080/blocking    # Load test

# WHAT IF... we used virtual threads!?
# IDE: add to properties file: spring.threads.virtual.enabled=true
# Set Tomcat threads to 1
curl http://localhost:8080/blocking    # Smoke test
hey -n 300 -c 100 http://localhost:8080/blocking    # Load test
