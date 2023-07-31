FROM openjdk:17-jdk-alpine
MAINTAINER edvicati@gmail.com
ADD target/belvo-poc-0.0.1-SNAPSHOT.jar belvo-poc-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "belvo-poc-0.0.1-SNAPSHOT.jar"]