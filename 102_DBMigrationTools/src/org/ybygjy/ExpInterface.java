package org.ybygjy;


/**
 * 扩展接口
 * <ol>
 * <li>提供统一的外部逻辑应用处理规范</li>
 * </ol>
 * @author WangYanCheng
 * @version 2012-10-22
 */
public interface ExpInterface {
    public void beforeMigration();
    public void afterMigration();
}
