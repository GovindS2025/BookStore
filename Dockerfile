# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the JAR file with the correct name from pom.xml
COPY --from=build /app/target/spring-boot-app-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables for Spring Boot
ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]