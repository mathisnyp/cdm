FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

EXPOSE 8080

COPY build/libs/communication-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
