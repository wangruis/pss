#!/bin/bash

#需要启动的Java主程序

APP_NAME=hlms-monitor-service
BASE_HOME=$(dirname $(cd `dirname $0`; pwd))
EXEC_JAR=$(find ${BASE_HOME}/lib/ -name ${APP_NAME}*.jar)
ROOT_NAME=root
HLMS_NAME=hlms
CURRENT_NAME=$(whoami)

###################################
#(函数)判断程序是否已启动
#
#说明：
#使用JDK自带的JPS命令及grep命令组合，准确查找pid
#jps 加 l 参数，表示显示java的完整包路径
#使用awk，分割出pid ($1部分)，及Java程序名称($2部分)
###################################
#初始化psid变量（全局）

psid=0

checkpid() {
  javaps=`jps -l | grep $APP_NAME`
  if [ -n "$javaps" ]; then
    psid=`echo $javaps | awk '{print $1}'`
  else
    psid=0
  fi
}

###################################
#(函数)启动程序
#
#说明：
#1. 首先调用checkpid函数，刷新$psid全局变量
#2. 如果程序已经启动（$psid不等于0），则提示程序已启动
#3. 如果程序没有被启动，则执行启动命令行
#4. 启动命令执行后，再次调用checkpid函数
#5. 如果步骤4的结果能够确认程序的pid,则打印[OK]，否则打印[Failed]
#注意：echo -n 表示打印字符后，不换行
#注意: "nohup 某命令 >/dev/null 2>&1 &" 的用法
###################################

echo "============================================================"

checkpid
if [ $psid -ne 0 ]; then
  echo "warn: $APP_NAME already started! (pid=$psid)"
else
  cd ..
  if [ ! -d "./logs/" ]; then
    mkdir logs
    chown -R hlms:hlms logs
  fi
  if [ ! -n "$1" ]; then
    if [ "$CURRENT_NAME" == "$ROOT_NAME" ]; then
      su - hlms <<EOF
cd $BASE_HOME/bin
./start.sh
exit
EOF
	elif [ "$CURRENT_NAME" == "$HLMS_NAME" ]; then
	  echo -n "Starting $APP_NAME ... "
      JAVA_CMD="java -Xms512m -Xmx512m -jar $EXEC_JAR > ./logs/console.log &"
      eval "$JAVA_CMD"
	else
	  echo -n "current user is $CURRENT_NAME, please use $ROOT_NAME or $HLMS_NAME"
	fi
  else
	if [ "$CURRENT_NAME" == "$ROOT_NAME" ]; then
      su - hlms <<EOF
cd $BASE_HOME/bin
./start.sh $1
exit
EOF
	elif [ "$CURRENT_NAME" == "$HLMS_NAME" ]; then
	  echo -n "Starting $APP_NAME "$1" mode ... "
      JAVA_CMD="java -Xms512m -Xmx512m -jar $EXEC_JAR --spring.profiles.active=$1 > ./logs/console.log &"
      eval "$JAVA_CMD"
	else
	  echo -n "current user is $CURRENT_NAME, please use $ROOT_NAME or $HLMS_NAME"
	fi
  fi
  checkpid
  if [ $psid -ne 0 ]; then
    echo "(pid=$psid) [OK]"
  else
    echo "[Failed]"
  fi
fi
echo "============================================================"
