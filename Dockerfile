FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/api-ecommerce-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} api-ecommerce.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-ecommerce.jar"]