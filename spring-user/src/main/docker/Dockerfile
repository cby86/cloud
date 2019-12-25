FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD spring-user-exec.jar spring-user-exec.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /user-exec.jar"]
EXPOSE 8082