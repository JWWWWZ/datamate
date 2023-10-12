# 基础环境信息搜集工具
## 下载工具

```
cd ~
wget http://gitlab.devdemo.trs.net.cn/open/bugmate/raw/master/src/main/shell/infra_info_collect.sh
```

## 运行工具

```
sh infra_info_collect.sh
```

~~~~  
开始搜集本机基本信息...
完成
开始搜集磁盘信息...
完成
开始测试本机和其他机器（通常需要测试 Redis、数据库 等）之间的网络通信情况，请输入目标机器的IP地址（或域名），多个以英文逗号分隔：192.168.210.105,192.168.210.206
开始 ping 192.168.210.105 500 次...
完成
开始 ping 192.168.210.206 500 次...
完成
开始测试磁盘读写情况，请输入要测试的目录（通常需要测试 分发目录、发布目录，例如 /mnt/TRSData,/TRS/HyCloud/IIP/data/WCMData），多个以英文逗号分隔：/TRS/mnt,/TRS/HyCloud/IIP/data/WCMData
开始测试写入 /TRS/mnt/test_disk_wr_20220713192702.log 30 次 ...
完成
开始测试读取 /TRS/mnt/test_disk_wr_20220713192702.log 30 次 ...
完成
已删除测试文件 /TRS/mnt/test_disk_wr_20220713192702.log
开始测试写入 /TRS/HyCloud/IIP/data/WCMData/test_disk_wr_20220713192712.log 30 次 ...
完成
开始测试读取 /TRS/HyCloud/IIP/data/WCMData/test_disk_wr_20220713192712.log 30 次 ...
完成
已删除测试文件 /TRS/HyCloud/IIP/data/WCMData/test_disk_wr_20220713192712.log
数据搜集完毕!
正在压缩...
a infra_20220713192422
a infra_20220713192422/basic_info_20220713192422.log
a infra_20220713192422/df_20220713192424.log
a infra_20220713192422/test_disk_wr_20220713192702.log
a infra_20220713192422/test_disk_wr_20220713192712.log
a infra_20220713192422/ping_192.168.210.206_20220713192539.log
a infra_20220713192422/ping_192.168.210.105_20220713192447.log

压缩完成： 12K	infra_20220713192422.tar.gz
请将 infra_20220713192422.tar.gz 提供给研发分析。
~~~~  