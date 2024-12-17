# Étape 1 : Construire l'application avec Maven
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY fournisseurIdentiteApi/ .  # Copier le contenu du dossier fournisseurIdentiteApi
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser OpenJDK pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
