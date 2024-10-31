FROM eclipse-temurin:21-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/trafficManagement-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} trafficManagement.jar
ENTRYPOINT ["java","-jar","/trafficManagement.jar"]