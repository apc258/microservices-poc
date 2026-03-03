# ===== Stage 1: build (compilar con Maven + Java 17) =====
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos primero el pom para aprovechar cache de dependencias
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copiamos el código y compilamos
COPY src ./src
RUN mvn -q -DskipTests clean package


# ===== Stage 2: run (ejecutar solo con JRE) =====
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiamos el jar construido
COPY --from=build /app/target/*.jar app.jar

# Documentación del puerto (realmente lo define SERVER_PORT)
EXPOSE 8081

# Ejecuta el microservicio
ENTRYPOINT ["java","-jar","/app/app.jar"]
