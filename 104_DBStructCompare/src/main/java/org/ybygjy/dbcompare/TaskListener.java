package org.ybygjy.dbcompare;
/**
 * 事务状态监听
 * @author WangYanCheng
 * @version 2011-10-8
 */
public interface TaskListener {
    /**
     * 任务执行前调用
     * @param taskInst 任务实例
     */
    public void beforeExecute(Task taskInst);
    /**
     * 任务执行后调用
     * @param taskInst 任务实例
     */
    public void afterExecute(Task taskInst);
}
