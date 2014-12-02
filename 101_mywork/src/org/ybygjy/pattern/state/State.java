package org.ybygjy.pattern.state;
/**
 * 定义所有状态接口规范
 * @author WangYanCheng
 * @version 2010-11-13
 */
public interface State {
    /**
     * 投入钱币
     */
    public void insertQuarter();
    /**
     * 退回钱币
     */
    public void enjectQuarter();
    /**
     * 转动曲柄
     */
    public void turnCrank();
    /**
     * 发放
     */
    public void dispense();
}
