FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
RUN apt-get install maven -y
COPY . .
RUN chmod +x mvnw
RUN ./mvnw spring-boot:run

FROM openjdk:21-jdk-slim
EXPOSE 8080

ENV DATABASE_URL="${DATABASE_URL}"
ENV DATABASE_USERNAME="${DATABASE_USERNAME}"
ENV DATABASE_PASSWORD="${DATABASE_PASSWORD}"
ENV SECRET_KEY="${SECRET_KEY}"

COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
