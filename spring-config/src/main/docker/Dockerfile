FROM openjdk:8-jdk-alpine
VOLUME /var/datalogs
ADD spring-config-exec.jar spring-config-exec.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /spring-config.jar"]
EXPOSE 8088