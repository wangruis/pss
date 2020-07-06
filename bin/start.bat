::windows启动脚本
@echo off
echo purchase-sale-storage-v1.0.0
set APP_NAME=purchase-sale-storage-v1.0.0.jar
::set CONFIG= -Dlogging.path=logs -Dspring.config.location=../config/application.yml
set CONFIG= -Dlogging.path=logs

set DEBUG_OPTS=
if ""%1"" == ""debug"" (
   set DEBUG_OPTS= -Xloggc:logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./logs
   goto debug
)

echo "Starting the %APP_NAME%..."
cd ..
java -Xms512m -Xmx512m -jar lib/%APP_NAME%
goto end
:debug
echo "Starting the debug mode"
cd ..
java -Xms512m -Xmx512m -jar lib/%APP_NAME% --spring.profiles.active=dev %DEBUG_OPTS%
goto end

:end
pause
