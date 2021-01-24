#!/bin/bash

# 进入工作目录
workDir=$(cd "$(dirname "$0")"; cd ../; pwd)
cd ${workDir}
# 定义bin、lib、config、logs目录
binDir=${workDir}/bin
libDir=${workDir}/lib
configDir=${workDir}/config
logsDir=${workDir}/logs

# 这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=data-api-1.0.jar

# 使用说明，用来提示输入参数
function usage() {
    echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
    echo "  start     --启动"
    echo "  stop      --停止"
    echo "  restart   --重启"
    echo "  status    --查看状态"
    exit 1
}

# 检查程序是否在运行
function is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

# 启动方法
function start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup java -jar ${libDir}/$APP_NAME > /dev/null 2>&1 &
  fi
}

# 停止方法
function stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi
}

# 输出运行状态
function status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

# 重启
function restart(){
  stop
  start
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac