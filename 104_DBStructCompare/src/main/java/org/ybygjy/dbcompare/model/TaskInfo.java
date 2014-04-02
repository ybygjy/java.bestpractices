package org.ybygjy.dbcompare.model;

/**
 * 任务实体信息
 * @author WangYanCheng
 * @version 2011-10-8
 */
public class TaskInfo {
    /** 任务名称 */
    private String taskName;
    /** 任务实现类 */
    private String taskClassName;
    /** 任务描述 */
    private String taskDesc;
    /** 源用户 */
    private String srcUser;
    /** 参照用户 */
    private String targetUser;
    /** 任务类型 */
    private MetaType metaType;
    /** isFinished*/
    private boolean isFinished;
    /**
     * 取任务名称
     * @return taskName taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 存储任务名称
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 取任务具体实例类
     * @return taskClassName taskClassName
     */
    public String getTaskClassName() {
        return taskClassName;
    }

    /**
     * 存储任务实例类
     * @param taskClassName taskClassName
     */
    public void setTaskClassName(String taskClassName) {
        this.taskClassName = taskClassName;
    }

    /**
     * 取任务描述信息
     * @return taskDesc
     */
    public String getTaskDesc() {
        return taskDesc;
    }

    /**
     * 存储任务描述信息
     * @param taskDesc taskDesc
     */
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * 取源用户
     * @return srcUser srcUser
     */
    public String getSrcUser() {
        return srcUser;
    }

    /**
     * 存储源用户
     * @param srcUser srcUser
     */
    public void setSrcUser(String srcUser) {
        this.srcUser = srcUser;
    }

    /**
     * 取目标(参照)用户
     * @return targetUser targetUser
     */
    public String getTargetUser() {
        return targetUser;
    }

    /**
     * 存储目标(参照)用户
     * @param targetUser targetUser
     */
    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    /**
     * 取任务类型标记
     * @return metaType metaType
     */
    public MetaType getTaskType() {
        return metaType;
    }

    /**
     * 存储任务类型标记
     * @param metaType {@link MetaType}
     */
    public void setTaskType(MetaType metaType) {
        this.metaType = metaType;
    }

    /**
     * 取当前任务执行状态
     * @return isFinished {true:完成;false:未完成}
     */
    public boolean isFinished() {
        return isFinished;
    }
    /**
     * 存储任务执行状态
     * @param isFinished {true:完成;false:未完成}
     */
    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "TaskInfo [任务名称=" + taskName + ", 任务实现类=" + taskClassName + ", 任务描述="
            + taskDesc + ", 原始用户=" + srcUser + ", 参照用户=" + targetUser + ", 任务类别标记=" + metaType
            + ", 任务状态=" + isFinished + "]";
    }
}
