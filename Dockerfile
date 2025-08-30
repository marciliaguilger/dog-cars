FROM openjdk:17-jdk-slim AS builder

WORKDIR /workspace
COPY . .
RUN ./gradlew clean build --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]