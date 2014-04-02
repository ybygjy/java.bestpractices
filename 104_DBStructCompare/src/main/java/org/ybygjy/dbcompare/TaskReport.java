package org.ybygjy.dbcompare;

import java.io.OutputStream;

import org.ybygjy.dbcompare.model.ContextModel;


/**
 * 
 * @author WangYanCheng
 * @version 2011-10-9
 */
public interface TaskReport {
    /**
     * 生成报表
     * @param contextModel 公共域模型管理实例集
     */
    public void generateReport(ContextModel[] commonModel);
    /**
     * 临时解决方案，参数信息
     * @param srcUser 原始用户
     */
    public void setSrcUser(String srcUser);
    /**
     * 临时解决方案，参数信息
     * @param targetUser 参照用户
     */
    public void setTargetUser(String targetUser);
    /**
     * 临时解决方案，负责转储报表内容
     * @param ous 转储流对象实例
     */
    public void setReOutputStream(OutputStream ous);
}
