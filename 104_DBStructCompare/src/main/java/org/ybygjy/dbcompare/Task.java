package org.ybygjy.dbcompare;

import java.sql.Connection;

import org.ybygjy.dbcompare.model.ContextModel;


/**
 * 定义任务公共行为
 * @author WangYanCheng
 * @version 2011-10-8
 */
public interface Task {
    /**
     * 执行任务
     */
    public void execute();

    /**
     * 添加任务监听器
     * @param taskListener 任务监听实例
     */
    public void addListener(TaskListener taskListener);

    /**
     * 删除任务监听器
     * @param taskListener 任务监听实例
     */
    public void removeListener(TaskListener taskListener);

    /**
     * 任务需要与数据库交互
     * @param conn 数据库连接
     */
    public void setConn(Connection conn);

    /**
     * 返回通用模型实例
     * @return contextModel 模型实例
     */
    public ContextModel getCommonModel();
}
