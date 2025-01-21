# Use a multi-stage build to optimize image size
FROM maven:3.6.3-openjdk-20 AS build
WORKDIR /app
COPY ./netflix /app
RUN mvn clean package -DskipTests

# Use a lightweight image to run the Spring Boot jar
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
