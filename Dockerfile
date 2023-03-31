FROM openjdk:17-jdk-alpine as build
COPY . /usr/app
WORKDIR /usr/app
RUN chmod +x mvnw && ./mvnw clean package

ARG VERSION
FROM openjdk:17-jdk-alpine
LABEL version=$VERSION
COPY --from=build /usr/app/target/*.jar winnocare-backend-image.jar
#
#USER spring:spring
#COPY /target/winnocare-backend-image.jar winnocare-backend-image.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/winnocare-backend-image.jar"]
#ADD target/winnocare-backend-image.jar winnocare-backend-image.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/winnocare-backend-image.jar"]