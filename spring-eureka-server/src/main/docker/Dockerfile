FROM openjdk:8-jdk-alpine
VOLUME /var/datalogs
ADD spring-eureka-server-exec.jar spring-eureka-server-exec.jar
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /spring-eureka-server-exec.jar"]
EXPOSE 8761