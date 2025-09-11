FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /Backend

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /Backend

COPY --from=build /Backend/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]