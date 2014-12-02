package org.ybygjy.web.log;

import org.apache.log4j.PropertyConfigurator;


/**
 * 日志管理
 * @author WangYanCheng
 * @version 2010-8-16
 */
public class LogMgr {
    /**
     * 初始化日志管理器
     * @param confFileUrl 配置文件地址
     */
    public void doInitLogProperty(String confFileUrl) {
        System.out.println("日志管理器==>".concat(confFileUrl));
        PropertyConfigurator.configure(confFileUrl);
    }
}
