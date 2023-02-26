FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/winnocare-backend-image.jar winnocare-backend-image.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/winnocare-backend-image.jar"]