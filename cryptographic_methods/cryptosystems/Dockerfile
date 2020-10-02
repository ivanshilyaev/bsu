FROM openjdk:14-jdk-alpine
ADD . /src
WORKDIR /src
EXPOSE 5000
ENTRYPOINT ["java","-jar","build/libs/cryptosystems-0.0.1-SNAPSHOT.war"]