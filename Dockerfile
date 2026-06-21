# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 as builder

WORKDIR /build

# Copy the pom.xml and source code
COPY sistema-bancario/pom.xml .
COPY sistema-bancario/src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /build/target/*.jar app.jar

# Expose the Spring Boot default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

