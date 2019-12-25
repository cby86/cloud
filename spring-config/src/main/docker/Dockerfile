FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD spring-config-exec.jar spring-config-exec.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /config-server-exec.jar"]
EXPOSE 8761