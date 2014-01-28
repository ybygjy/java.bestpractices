package org.ybygjy.ctx;

import org.ybygjy.MigrationContext;

/**
 * {@link MigrationContext}工厂
 * @author WangYanCheng
 * @version 2012-10-17
 */
public class MigrationContextFactory {
    /**singleton pattern*/
    private static MigrationContextFactory mcfInst = new MigrationContextFactory();
    /**{@link MigrationContext}实例*/
    private static MigrationContext mcInst;
    /**
     * Constructor
     */
    public MigrationContextFactory() {
        //TODO 此处存在多线程问题，今后多线程版本中，考虑采用多线程机制处理_比对后设置
        mcInst = new MigrationContextImpl();
    }
    /**
     * 取{@link MigrationContext} 实例
     * @return mcfInst {@link MigrationContextFactory}
     */
    public static final MigrationContextFactory getInstance() {
        return mcfInst;
    }
    /**
     * 取{@link MigrationContext} 实例
     * @return mcInst {@link MigrationContext}
     */
    public MigrationContext getCtx() {
        return mcInst;
    }
}
