FROM gradle:7.4.2-jdk17 AS builder

# Configura ambiente
USER root
WORKDIR /app

# Copia arquivos de build primeiro (para melhor cache)
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle
RUN chmod +x gradlew

# Baixa dependências primeiro
RUN ./gradlew --no-daemon dependencies

# Copia código e faz build
COPY src src
RUN ./gradlew --no-daemon clean build -x test

# Runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]