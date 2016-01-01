#!/bin/bash
#echo 'Hello';
#str='http://www.你的域名.com/cut-string.html'
#echo ${str%/*};
#echo ${str%%/*}
#echo ${str#*/}
#echo ${str##*/}
#echo ${str:0:5}
#echo ${str:7}
#echo ${str:0-15}
#echo ${str:0-15:10}
#tmpArr=(1 2 3);
#tmpArr[${#tmpArr[*]}]=$str;
#for tmpVar in ${tmpArr[@]}
#do
#    echo $tmpVar;
#done
#str='http://svn.service.com/repos';
#if [ $str == 'http://svn.service.com/repos' ] 
#then
#    echo "$str equals svn.service.com/repos";
#fi
#read animal
#echo -n "The $animal has "
#case $animal in 
#    horse | dog | cat ) echo -n "four";;
#    man | kangaroo ) echo -n "two";;
#    *) echo -n "an unknown number of ";;
#esac
#echo " legs."
tmpArr=(1 1 2 2 3 3 4 4 5 5 6);
tmpArr2=(1);
function isContains() {
    isCRtnFlag=0;
    for isTmpVal in ${tmpArr2[@]}
    do
        if [ $isTmpVal = $1 ]
        then
            isCRtnFlag=1;
            break;
        fi
    done
    return $isCRtnFlag; 
}
for tmpVal in ${tmpArr[@]}
do
    isContains $tmpVal;
    func_ret=$?;
    echo "$tmpVal::$func_ret";
    #if [ $func_ret == '1' ]
    #then
    #    echo "";
    #else
    #    echo "unmatched$func_ret:$tmpVal";
    #fi
    #echo "What happend=>$tmpVal::$func_ret";
done
echo '=================';

