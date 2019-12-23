FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD config-server-exec.jar config-server-exec.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /config-server-exec.jar"]
EXPOSE 8761