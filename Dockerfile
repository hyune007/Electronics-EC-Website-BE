# ===== Stage 1: Build =====
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml truoc de cache dependencies layer
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code va build
COPY src ./src
RUN mvn package -DskipTests -B

# ===== Stage 2: Run =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy JAR tu stage build
COPY --from=build /app/target/*.jar app.jar

# Copy Firebase service account key (neu co)
# COPY serviceAccountKey.json .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
