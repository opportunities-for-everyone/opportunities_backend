# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src/
COPY checkstyle.xml .
RUN mvn clean package -DskipTests -Dcheckstyle.skip=true

# Stage 2: Create the final lightweight runtime image
FROM openjdk:17-slim-buster
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
