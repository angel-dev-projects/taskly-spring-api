FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .
RUN /bin/bash -c "./mvnw clean package"

FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY --from=build /target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]