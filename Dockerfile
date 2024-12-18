FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./target/metric-0.0.1-SNAPSHOT.jar /app/metric.jar

CMD ["java", "-jar", "/app/metric.jar"]