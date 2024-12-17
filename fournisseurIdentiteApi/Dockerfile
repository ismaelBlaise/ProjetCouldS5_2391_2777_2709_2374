# Étape 1 : Utiliser une image JDK pour construire l'application
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Copier les fichiers de projet dans le conteneur
COPY . /app

# Donner les permissions d'exécution à mvnw et construire le projet
RUN chmod +x ./mvnw
RUN ./mvnw package -DskipTests

# Étape 2 : Utiliser une image JDK légère pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port de l'application Spring Boot
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
