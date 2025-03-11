Docerfile давать окончание yml или пусть будет текстовый документ?
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/Sofia-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]