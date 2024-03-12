FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
RUN apt-get install maven -y
COPY . .
RUN chmod +x mvnw
RUN /bin/bash -c "./mvnw spring-boot:run"

FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
