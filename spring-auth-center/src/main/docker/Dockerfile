FROM openjdk:8-jdk-alpine
VOLUME /var/datalogs
ADD spring-auth-center-exec.jar spring-auth-center-exec.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /spring-auth-center-exec.jar"]
EXPOSE 8086