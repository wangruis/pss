#!/bin/bash

#需要启动的Java主程序

APP_NAME=purchase-sale-storage

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
#(函数)停止程序
#
#说明：
#1. 首先调用checkpid函数，刷新$psid全局变量
#2. 如果程序已经启动（$psid不等于0），则开始执行停止，否则，提示程序未运行
#3. 使用kill -9 pid命令进行强制杀死进程
#4. 执行kill命令行紧接其后，马上查看上一句命令的返回值: $?
#5. 如果步骤4的结果$?等于0，则打印[OK]，否则打印[Failed]
#6. 为了防止java程序被启动多次，这里增加反复检查进程，反复杀死的处理（递归调用stop）
#注意：echo -n 表示打印字符后，不换行
#注意: 在shell编程中，"$?" 表示上一句命令或者一个函数的返回值
###################################

checkpid

echo "============================================================"
if [ $psid -ne 0 ]; then
  echo -n "Stopping $APP_NAME ... (pid=$psid) "
  eval "kill -9 $psid"
  if [ $? -eq 0 ]; then
    echo "[OK]"
  else
    echo "[Failed]"
  fi
else
  echo "warn: $APP_NAME is not running!"
fi
echo "============================================================"
