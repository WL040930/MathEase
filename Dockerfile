# Use a base image that contains JDK 21
FROM openjdk:21-jdk-slim

# Copy the Spring Boot jar to the working directory
COPY target/*.jar app.jar

# Expose the port your application will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]
