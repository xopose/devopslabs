FROM maven:3.9.9-amazoncorretto-21-debian AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY .. .
RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21.0.7_6-jre-ubi9-minimal
WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]