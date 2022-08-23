FROM eclipse-temurin:18-jre-alpine
ARG JAR_FILE=target/*spring-boot.jar
COPY $JAR_FILE /app.jar
CMD ["java", "-jar", "/app.jar"]