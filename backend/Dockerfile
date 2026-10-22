# syntax=docker/dockerfile:1

# ---- Étape 1 : construction avec un JDK 25 ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# On copie d'abord le wrapper Maven et le pom : les dépendances ne sont retéléchargées
# que si le pom change, pas à chaque modification du code.
COPY .mvn .mvn
COPY mvnw pom.xml ./
# mvnw peut avoir des fins de ligne Windows (CRLF) selon la configuration Git du poste
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw && ./mvnw -q dependency:go-offline

COPY src src
RUN ./mvnw -q -DskipTests package

# ---- Étape 2 : exécution avec un JRE 25, sans droits root ----
FROM eclipse-temurin:25-jre
WORKDIR /app

RUN groupadd --system kolekta && useradd --system --gid kolekta --no-create-home kolekta
COPY --from=build --chown=kolekta:kolekta /app/target/kolekta-*.jar /app/app.jar

USER kolekta
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
