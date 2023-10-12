# MySQL General Log 分析工具
## 安装JDK，并配置好JDK的环境变量  

```
cd ~
wget http://gitlab.devdemo.trs.net.cn/open/bugmate/raw/master/src/main/java/jdk1.8.311.tar.gz  
wget http://gitlab.devdemo.trs.net.cn/open/bugmate/raw/master/src/main/shell/install_jdk.sh  
sh install_jdk.sh  
```

## 编译源码  

```
cd ~
javac MySQLGeneralLogParser.java
```

## 运行工具  

```
java MySQLGeneralLogParser
```

>请输入要解析的 MySQL/MariaDB general log 的全路径：  

_/Users/Downloads/general.log_  

~~~~  
开始解析：/Users/linchen/Downloads/general.log  
总行数：249190  
起始时间：220705 16:44:50  
结束时间：220705 16:46:35  
53109 条 		set  autocommit=1  
53046 条 		set  autocommit=0  
38778 条 		commit  
16035 条 		select 1  
15053 条 		rollback  
140 条 		select 1  from dual  
37 条 		set  sql_select_limit=1  
43 条 		set  sql_select_limit=default  
7 条 		set  sql_select_limit=5  
18 条 		set  names utf8mb4  
17 条 		set  character_set_results = null  
2 条 		set  names utf8  
1 条 		set  profiling = 1  
16874 条 		select from trs_appendix appendix0_  
2433 条 		select from trs_govmsgbox govmsgbox0_  
... 
~~~~  