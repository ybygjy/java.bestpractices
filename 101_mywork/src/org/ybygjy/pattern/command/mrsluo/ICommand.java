package org.ybygjy.pattern.command.mrsluo;

/**
 * 封装命令
 * @author WangYanCheng
 * @version 2011-8-1
 */
public interface ICommand {
    /**
     * 取命令操作对象
     * @return 命令操作对象
     */
    public String getObject();
    /**
     * 设置命令操作对象
     * @param obj 命令操作对象
     */
    public void setObject(String obj);
    /**
     * 重做
     */
    public void redo();
    /**
     * 撤消
     */
    public void undo();
}
