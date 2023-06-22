FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
ADD target/comparador-api.jar comparador-api.jar
ENTRYPOINT ["java","-jar","/comparador-api.jar"]