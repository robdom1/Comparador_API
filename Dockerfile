FROM eclipse-temurin:17-jdk-alpine
ADD target/comparador-api.jar comparador-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/comparador-api.jar"]