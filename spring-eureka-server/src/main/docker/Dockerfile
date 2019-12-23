FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD eureka-server-exec.jar eureka-server-exec.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /eureka-server-exec.jar"]
EXPOSE 8761