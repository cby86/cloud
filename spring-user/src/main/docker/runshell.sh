#docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=spring-user
#删除正在运行容器
for line in `docker ps -a | grep "$SERVER_NAME" | awk '{print $1}'`
do
 docker stop $line
 echo "停止容器"+$line
 docker rm $line
 echo "删除容器"+$line
done


for line in `docker images | grep "<none>" | awk '{print $3}'`
do
  for container in `docker ps -a| grep "$line" | awk '{print $1}'`
  do
   docker stop $container
   echo "停止容器"+$container
   docker rm $container
   echo "删除容器"+$container
  done

  docker rmi $line
  echo "删除镜像"+$line
done