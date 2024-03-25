FROM amazoncorretto:17
#
#WORKDIR /app
#
#EXPOSE 8080
#
#COPY build/libs/backend-for-frontend-0.0.1-SNAPSHOT.jar app.jar
#
#ENTRYPOINT ["java","-jar","app.jar"]
#FROM adoptopenjdk/openjdk11:alpine-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Spring Boot application JAR file into the container
COPY build/libs/backend-for-frontend-0.0.1-SNAPSHOT.jar app.jar

# Copy the application.yml file into the container
COPY src/main/resources/application.yml /app/application.yml

# Expose the port your application runs on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "app.jar", "--spring.config.location=application.yml"]
