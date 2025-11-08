FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/weather-app-0.0.1-SNAPSHOT.jar /app/app.jar
RUN adduser -D appuser
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
