# Step 1: Build the multi-module project using a Maven image with JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the master pom.xml and all child module directories
COPY pom.xml .
COPY backend ./backend
COPY frontend ./frontend

# Compile and package all services using the root orchestrator
RUN mvn clean package -DskipTests

# Step 2: Create the slim runtime image to run just the patient-service
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the compiled executable jar from the patient-service target folder
COPY --from=build /app/backend/patient-service/target/patient-service-1.0.0-SNAPSHOT.jar app.jar

# Render injects a dynamic $PORT variable. We expose it and run the application.
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]