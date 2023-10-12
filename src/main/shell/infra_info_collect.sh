#!/bin/bash

mkdir -p /tmp
pingcount=500
wrcount=30

# 搜集本机信息
collect_basic_info()
{
local infodir=$1
if [ -n "$infodir" ]; then
  local filename=basic_info_`date "+%Y%m%d%H%M%S"`.log
  echo "开始搜集本机基本信息..."
  uname -a >> ./"$infodir"/"$filename"
  hostname >> ./"$infodir"/"$filename"
  ifconfig >> ./"$infodir"/"$filename"
  sleep 1
  echo "完成"
  sleep 1
else
 echo "调用 collect_basic_info 函数：缺少 infodir 参数"
fi
}
# 搜集磁盘数据
collect_disk_info()
{
local infodir=$1
if [ -n "$infodir" ]; then
  local filename=df_`date "+%Y%m%d%H%M%S"`.log
  echo "开始搜集磁盘信息..."
  df -h > ./"$infodir"/"$filename"
  sleep 1
  echo "完成"
  sleep 1
else
 echo "调用 collect_disk_info 函数：缺少 infodir 参数"
fi
}

# 搜集ping数据
collect_ping()
{
local pinghost=$1
local infodir=$2

if [ -n "$pinghost" ] && [ -n "$infodir" ]; then
  local current=`date "+%Y%m%d%H%M%S"`

  echo "开始 ping $pinghost $pingcount 次..."
  local pingfilename=ping_"$pinghost"_"$current".log
  ping $pinghost -i 0.1 -c $pingcount >> ./"$infodir"/"$pingfilename"
  echo "完成"
  sleep 1
else
  echo "调用 collect_ping 函数：缺少 pinghost 或 infodir 参数"
fi
}
# 搜集磁盘读写能力
collect_write_read_disk()
{

local current=`date "+%Y%m%d%H%M%S"`
local testwrdir=$1
local infordir=$2
if [ -n "$testwrdir" ] || [ -n "$infodir" ]; then
    local testwritefilename=test_disk_wr_"$current".log
      local testwritefile="$testwrdir"/"$testwritefilename"
    echo "开始测试写入 $testwritefile $wrcount 次 ..."
      for ((i=1;i<=$wrcount;i++)); do
          echo "第 $i 次写入 $testwritefile ，时间戳： " `date "+%Y%m%d%H%M%S"` >> $testwritefile
            #echo "第 $i 次测试写入 $testwritefile"
        sleep 0.1
      done
    echo "完成"
    sleep 1
    echo "开始测试读取 $testwritefile $wrcount 次 ..."
      for ((i=1;i<=$wrcount;i++)); do
        echo "第 $i 次读取文件状态 $testwritefile ，时间戳： " `date "+%Y%m%d%H%M%S"` >> $testwritefile
        stat $testwritefile >> $testwritefile
        sleep 0.1
      done
    cat $testwritefile >> ./"$infodir"/$testwritefilename
      echo "完成"
    sleep 1
      rm -f $testwritefile
      echo "已删除测试文件 $testwritefile"
    sleep 1
else
  echo "调用 collect_write_read_disk 函数：缺少 testwrdir 或 infodir 参数"
fi
}



current=`date "+%Y%m%d%H%M%S"`
infodir=infra_"$current"
compressedinfodir="$infodir".tar.gz
mkdir "$infodir"
collect_basic_info "$infodir"
collect_disk_info "$infodir"



read -p "开始测试本机和其他机器（通常需要测试 Redis、数据库 等）之间的网络通信情况，请输入目标机器的IP地址（或域名），多个以英文逗号分隔：" pinghosts

if [ -z "$pinghosts" ]; then
  echo "未接收到输入参数，跳过"
else
  pinghostarray=(${pinghosts//,/ })
  for pinghost in ${pinghostarray[@]}
  do
    collect_ping "$pinghost" "$infodir"
  done
fi

read -p "开始测试磁盘读写情况，请输入要测试的目录（通常需要测试 分发目录、发布目录，例如 /mnt/TRSData,/TRS/HyCloud/IIP/data/WCMData），多个以英文逗号分隔：" wrdirs

if [ -z "$wrdirs" ]; then
  echo "未接收到输入参数，跳过"
else
  wrdirarray=(${wrdirs//,/ })
  for wrdir in ${wrdirarray[@]}
  do
    if [ -d "$wrdir" ]; then
      continue;
    else
      echo "目录 $wrdir 不存在"
      exit 0
    fi
  done

  for wrdir in ${wrdirarray[@]}
  do
    collect_write_read_disk "$wrdir" "$infodir"
  done
fi


sleep 1
echo "数据搜集完毕!"
#echo `cd ./"$infodir" && du -sh *`
sleep 1
echo "正在压缩..."
echo `tar -cvzf "$compressedinfodir" "$infodir"`
sleep 1
echo "压缩完成：`du -sh $compressedinfodir`"
#rm -rf "$infodir"
#echo "已删除 $infodir"
sleep 1
echo "请将 $compressedinfodir 提供给研发分析。"
exit 0