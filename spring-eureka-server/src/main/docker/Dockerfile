FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD eureka-server.jar eureka-server.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /eureka-server.jar"]
EXPOSE 8761