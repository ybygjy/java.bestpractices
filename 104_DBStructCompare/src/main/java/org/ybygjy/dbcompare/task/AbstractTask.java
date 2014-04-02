package org.ybygjy.dbcompare.task;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.ybygjy.dbcompare.Task;
import org.ybygjy.dbcompare.TaskListener;
import org.ybygjy.dbcompare.model.ContextModel;
import org.ybygjy.dbcompare.model.TaskInfo;


/**
 * 抽象定义任务公共实现
 * @author WangYanCheng
 * @version 2011-10-8
 */
public abstract class AbstractTask implements Task {
    /** 事件监听对象集合 */
    private List<TaskListener> taskListeners;
    /** 数据库连接实例 */
    protected Connection conn;
    /** 模型管理实例 */
    protected ContextModel contextModel;
    /** 原始用户*/
    protected String srcUser;
    /** 参照用户*/
    protected String targetUser;
    /**
     * 构造方法
     */
    public AbstractTask(String srcUser, String targetUser) {
        taskListeners = new ArrayList<TaskListener>();
        contextModel = new ContextModel();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setSrcUser(srcUser);
        taskInfo.setTargetUser(targetUser);
        taskInfo.setTaskClassName(this.getClass().getName());
        contextModel.setTaskInfo(taskInfo);
        this.srcUser = taskInfo.getSrcUser();
        this.targetUser = taskInfo.getTargetUser();
    }

    /**
     * {@inheritDoc} 子类必须实现此方法
     */
    public abstract void execute();

    /**
     * {@inheritDoc}
     */
    public void addListener(TaskListener taskListener) {
        taskListeners.add(taskListener);
    }

    /**
     * {@inheritDoc}
     */
    public void removeListener(TaskListener taskListener) {
        if (null != taskListener) {
            taskListeners.remove(taskListener);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * {@inheritDoc}
     */
    public ContextModel getCommonModel() {
        return contextModel;
    }

    /**
     * 事务执行前
     */
    protected void beforeListener() {
        for (TaskListener t : taskListeners) {
            if (null != t) {
                t.beforeExecute(this);
            }
        }
    }

    /**
     * 事务执行后
     */
    protected void afterListener() {
        for (TaskListener t : taskListeners) {
            if (null != t) {
                t.afterExecute(this);
            }
        }
    }
}
