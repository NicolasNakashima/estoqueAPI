FROM maven:3.8.3-openjdk-22 as Build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:22-jdk-slim

WORKDIR /app
COPY --from=Build /app/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]