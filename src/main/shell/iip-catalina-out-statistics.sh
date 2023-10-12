#!/bin/bash

read -p "请输入 catalina.out 日志文件的全路径：" logfile
# echo $logfile

if [ ! -e "$logfile" ]; then
  echo "文件 $logfile 不存在，程序退出"
  exit 0
fi

echo "ready...go..."


logstatistics=${logfile}.statistics
logstatisticsdetails=${logstatistics}.details
echo '' > $logstatistics
echo '' > $logstatisticsdetails

grep 'WCMLOG' $logfile | awk '{print $5}' | sort | uniq -c | sort -nr > ${logstatistics}
echo 统计结果： $logstatistics

cat $logstatistics | while read line ;
do
  echo '' >> $logstatisticsdetails

  num=`echo $line | awk '{print $1}'`
  echo "========  $num 次 ========" >> $logstatisticsdetails

  echo '' >> $logstatisticsdetails

  msg=`echo $line | awk '{print $2}'` 
  grep $msg  -m 2 -A 20 $logfile | head -20  >> $logstatisticsdetails

  echo '' >> $logstatisticsdetails

done


echo 统计结果+堆栈信息： $logstatisticsdetails


echo "done!!!"
