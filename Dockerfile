FROM eclipse-temurin:21.0.3_9-jre
MAINTAINER "Abhishek M"
COPY target/ihmsserviceapi.jar  /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]
