FROM openjdk:eclipse-temurin
MAINTAINER adoptium
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} CrudLosCines.1.0.jar
ENTRYPOINT ["java","-jar","/CrudLosCines.1.0.jar"]