FROM eclipse-temurin:18-jre-alpine
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE /app.jar
CMD ["java", "-jar", "/app.jar"]