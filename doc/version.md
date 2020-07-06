部署说明：  
&ensp; &ensp; 打包：purchase-sale-storage-v1.0.0.tar.gz  
&ensp; &ensp; 解压：purchase-sale-storage-v1.0.0  
&ensp; &ensp; 文件目录:  
&ensp; &ensp; &ensp; &ensp;  -base 导出用 可忽略  
&ensp; &ensp; &ensp; &ensp; -bin 程序快捷启动文件 win系统执行start.bat linux系统执行start.sh文件  
&ensp; &ensp; &ensp; &ensp; -config 程序配置文件 运行前需要修改application.yml中的数据库连接地址用户名密码  
&ensp; &ensp; &ensp; &ensp; -db sql脚本 运行程序前先执行出示话脚本  
&ensp; &ensp; &ensp; &ensp; -doc 程序说明  
&ensp; &ensp; &ensp; &ensp; -lib 程序包  

2019-06-06
新增功能说明：
运行程序前需要执行DB文件下的updae_2019_06_04.sql sql脚本。