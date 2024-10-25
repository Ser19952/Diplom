FROM ubuntu:latest

EXPOSE 8080

COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
LABEL authors="helenastahova"
