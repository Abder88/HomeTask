# ---- Stage 1: Build the application ----
FROM maven:3.8.1-openjdk-17-slim AS builder
WORKDIR /app

# Copy only the pom first, to take advantage of Docker's layer caching
COPY pom.xml .
# Download all dependencies (this will be cached if you don't change pom.xml)
RUN mvn dependency:go-offline

# Now copy the whole project and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Stage 2: Run the application ----
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the Spring Boot JAR from the builder stage
COPY --from=builder /app/target/HomeTask-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "HomeTask-0.0.1-SNAPSHOT.jar"]

