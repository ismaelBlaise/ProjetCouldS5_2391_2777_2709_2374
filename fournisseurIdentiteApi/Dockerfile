# Étape 1 : Utiliser une image JDK pour construire l'application
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Copier le projet dans le conteneur
COPY . /app

# Construire le projet avec Maven
RUN ./mvnw package -DskipTests

# Étape 2 : Utiliser une image JDK pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port de l'application Spring Boot
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
