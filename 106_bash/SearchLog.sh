#!/bin/bash
#author WangYanCheng
#version 2015-08-29
#def_user="work"
#def_hosts="10.7.0.59 10.7.0.21 10.7.0.43 10.7.0.23 10.7.0.55"
def_user="root"
def_hosts="192.168.190.2"
def_path="/opt/logs/higo/201508/29/"
#给定服务地址和时间查日志
function doWork(){
    for host in $def_hosts
    do
        echo $host;
        ssh $def_user@$host "find $def_path -name debug.log|xargs grep 'account/login'|grep '18676666661'"
        echo $?
        ret=$?
        if [ 0 != $ret ]
        then
            echo "${green}"
        else
            echo "error ${green}"
        fi
    done
}
doWork

