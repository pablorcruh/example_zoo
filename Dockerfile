FROM openjdk:11-jre-slim-buster

EXPOSE 8080

COPY ./target/example_zoo-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "example_zoo-0.0.1-SNAPSHOT.jar"]