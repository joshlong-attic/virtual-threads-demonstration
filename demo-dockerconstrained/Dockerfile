# syntax=docker/dockerfile:1

FROM openjdk:21-jdk

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY docker-compose.yaml docker-compose.yaml

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]