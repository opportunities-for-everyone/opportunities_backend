# Етап 1: Збірка проекту
FROM maven:3.8.7-openjdk-17 AS builder
WORKDIR /application

# Копіюємо файли проєкту в Docker
COPY pom.xml .
COPY src ./src

# Збираємо проект
RUN mvn clean package -DskipTests

# Етап 2: Підготовка Spring Boot application
FROM openjdk:17-jdk-alpine
WORKDIR /application

# Копіюємо зібраний JAR-файл із попереднього етапу
COPY --from=builder /application/target/*.jar application.jar

# Виконуємо команди для оптимізації Spring Boot завантаження
RUN java -Djarmode=layertools -jar application.jar extract

# Копіюємо необхідні шари
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8080
