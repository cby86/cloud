FROM openjdk:8-jdk-alpine
VOLUME /var/datalogs
ADD spring-backend-center-exec.jar spring-backend-center-exec.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /spring-backend-center-exec.jar"]
EXPOSE 8083