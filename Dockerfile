# Stage 1: Build the Spring Boot app using Gradle
FROM gradle:8.4.0-jdk21 AS builder
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle build --no-daemon

# Stage 2: Run the app using a minimal Java runtime
FROM eclipse-temurin:21-jre
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]