FROM openjdk:17-jdk-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ADD /target/winnocare-backend-image.jar winnocare-backend-image.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/winnocare-backend-image.jar"]
#ADD target/winnocare-backend-image.jar winnocare-backend-image.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/winnocare-backend-image.jar"]