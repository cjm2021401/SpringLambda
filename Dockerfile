FROM openjdk:11-jdk-slim AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src



RUN ./gradlew build -x test


FROM openjdk:11-jre-slim
COPY --from=builder build/libs/application.jar application.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar" ,"/application.jar"]

