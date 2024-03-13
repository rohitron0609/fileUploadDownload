FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/fileUploadDownload-1.jar app.jar
ENTRYPOINT ["java", "-jar", "target/*.jar"]
