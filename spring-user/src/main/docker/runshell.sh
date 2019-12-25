#docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=spring-user
#删除正在运行容器
for line in `docker ps | grep "$SERVER_NAME" | awk '{print $1}'`
do
  docker stop $line
  echo "停止容器"+$line
  docker rm $line
  echo "删除容器"+$line
done

#删除镜像
for line in `docker images | grep "$SERVER_NAME" | awk '{print $3}'`
do
  docker rmi $line
  echo "删除镜像"+$line
done
