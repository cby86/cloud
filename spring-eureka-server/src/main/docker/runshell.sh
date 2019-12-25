#docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=spring-eureka-server
#删除正在运行容器
for line in `docker ps | grep "$SERVER_NAME" | awk '{print $1}'`
do
  docker stop $line
  echo "停止容器"+$line
  docker rm $line
  echo "删除容器"+$line
done

#如果同一版本多次发布,需要把之前的版本清楚
for line in `docker images | grep "<none>" | awk '{print $3}'`
do
  docker rmi $line
  echo "删除镜像"+$line
done

docker run  --restart=always -e JAVA_OPTS='-Deureka.client.serviceUrl.defaultZone=http://192.168.3.15:8761/eureka' -p 8762:8761 -d --name register-server2  $SERVER_NAME:latest
docker run  --restart=always -e JAVA_OPTS='-Deureka.client.serviceUrl.defaultZone=http://192.168.3.15:8762/eureka' -p 8761:8761 -d --name register-server1  $SERVER_NAME:latest