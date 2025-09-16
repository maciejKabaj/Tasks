# Use Gradle image to build the application
FROM gradle:8.4.0-jdk21 AS builder
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

# Skip tests to avoid DB connection error
RUN gradle build -x test --no-daemon

# Use a lightweight image to run the app
FROM eclipse-temurin:21-jre
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]