package org.ybygjy.dbcompare.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 负责定义模型的抽象管理,各领域模型的抽象管理
 * @author WangYanCheng
 * @version 2011-10-8
 */
public class ContextModel {
    private TaskInfo taskInfo;
    /** 任务原始数据集 */
    private Map<String, Object> taskRawDataColl = new HashMap<String, Object>();

    /**
     * 取任务信息实例
     * @return 任务信息实例
     */
    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    /**
     * 存储任务信息实例
     * @param taskInfo 任务信息实例
     */
    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    /**
     * 存储原始数据集
     * @param key 键
     * @param obj 值
     */
    public void putRawData(String key, Object obj) {
        this.taskRawDataColl.put(key, obj);
    }

    /**
     * 取原始数据集
     * @param key 键
     * @return rtnV 值对象/值集
     */
    public Object getRawData(String key) {
        return taskRawDataColl.containsKey(key) ? taskRawDataColl.get(key) : null;
    }
}
