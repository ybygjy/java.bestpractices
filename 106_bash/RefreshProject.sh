#!/bin/bash
baseUrl="http://svn.com/repos/";
baseDir="/Users/MLS/1001_mywork_new/";
trunkArr=(trunk doc sql );
branchesArr=(ariesbranches ariesfashionbranches ariesframeworkbranches ariesriskbranches branches dev_branches fe_branches hornbill_release_branches imserverbranches release_branches tag tags user_tag_new);
subBranchArr=();
tmpSubBranchArr=();
echo $baseUrl;
#检出或更新
function doCheckout() {
    urlPath=$1;
    dirPath=${baseDir}$2;
    if [ -d $dirPath -o -w $dirPath ]
    then
        echo "svn up $urlPath to $dirPath" >> $baseDir/svn_up.log;
        svn cleanup $dirPath;
        svn up $dirPath;
    else
        echo "svn checkout $urlPath to $dirPath" >> $baseDir/svn_checkout.log;
        svn checkout $urlPath $dirPath;
    fi
}
function inarray() {
    rtnFlag=0;
    for tmpVar in ${branchesArr[@]} 
    do
        if [ $tmpVar = $1 ] 
        then
            rtnFlag=1;
            break;
        fi
    done;
    return $rtnFlag;
}
function inarray4Trunk() {
    rtnFlag=0;
    for tmpVar in ${trunkArr[@]} 
    do
        if [ $tmpVar == $1 ] 
        then
            rtnFlag=1;
            break;
        fi
    done;
    return $rtnFlag;
}
#构造识别路径
function doBuildPath() {
    innerBuildPathValue="";
    trunkFlag=0;
    branchFlag=0;
    for trunkInfo in `svn list $1$2`
    do
        inarray4Trunk ${trunkInfo%/*};
        func_ret=$?;
        if [ $func_ret == '1' ]
        then
            innerBuildPathValue=$1$2${trunkInfo%/*};
            trunkFlag=1;
            break;
        fi
        inarray ${trunkInfo%/*};
        func_ret=$?;
        if [ $func_ret == '1' ]
        then
            branchFlag=1;
        fi
    done;
    #trunk标识与branch标识同时为零，这时需要检测下一级是否复合条件
    if [ $trunkFlag == 0 -a $branchFlag == 0  ]
    then
        echo "Need to checkout=>$3#$1$2" >> $baseDir/logs.log;
        #限制递归级别为2级
        #等于1说明是第一级，需要进行递归处理第二级，第二级如果不能识别trunk则检出第一级
        if [ $3 == 1 ]
        then
            for pathInfo in `svn list $1$2`
            do
                doBuildPath $1 $2$pathInfo 2;
            done;
        #第二级递归调用，处理了文件非目录情况
        elif [ $3 == 2 ]
        then
            tmpPath=$1$2;
            if [ ${tmpPath:0-1} == '/' ]
            then
                tmpPath=${tmpPath%/*}
            fi
            tmpPath=${tmpPath%/*}
            if [ $tmpPath != ${baseUrl%/*} ]
            then
                tmpArrVal=${#subBranchArr[*]};
                subBranchArr[$tmpArrVal]="$tmpPath";
                echo "Need to be checkout sub branches=>$3:$tmpPath" >> $baseDir/logs.log;
            fi
        fi
    elif [ $trunkFlag == 1  ]
    then
        echo $innerBuildPathValue >> $baseDir/pendings.log
        doCheckout $innerBuildPathValue $2${trunkInfo};
    fi
}
function isContains() {
    for isCTmpVal in ${tmpSubBranchArr[@]}
    do
        if [ $isCTmpVal == $1 ]
        then
            return 1;
        fi
    done;
    return 0;
}
function doCheckoutBatch() {
    for tmpVal in ${subBranchArr[@]}
    do
        isContains $tmpVal;
        innerfunc_ret=$?;
        if [ $innerfunc_ret != '1' ]
        then
            doCheckout $tmpVal ${tmpVal##*/};
            tmpArrVal=${#tmpSubBranchArr[*]};
            tmpSubBranchArr[$tmpArrVal]="$tmpVal";
        fi
    done;
}
#入口
function doWork() {
    for projectUri in `svn list ${baseUrl}`
    do
        doBuildPath $baseUrl $projectUri 1;
    done;
    doCheckoutBatch;
    svn up ~/1001_mywork_new/higoim/feTurbo
}
doWork;
