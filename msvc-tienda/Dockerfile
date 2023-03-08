FROM openjdk:19-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} .
ENTRYPOINT ["java","-jar","msvc-tienda-0.0.1-SNAPSHOT.jar"]