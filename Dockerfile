FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests


FROM tomcat:10.1-jdk17-temurin

COPY --from=builder /app/target/StudentManangement-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
