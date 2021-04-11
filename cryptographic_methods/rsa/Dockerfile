FROM openjdk:14-jdk-alpine
ADD . /src
WORKDIR /src
EXPOSE 5000
ENTRYPOINT ["java","-jar","build/libs/rsa-1.0-SNAPSHOT.jar"]
