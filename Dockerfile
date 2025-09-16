# Use OpenJDK as a base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file (Gradle puts it in build/libs)
COPY build/libs/*.jar app.jar

# Expose port (default for Spring Boot)
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]