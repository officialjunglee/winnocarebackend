FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
ADD target/winnocare-backend-image.jar home/winnocare-backend-image.jar
ENTRYPOINT ["java", "-jar", "/home/winnocare-backend-image.jar"]