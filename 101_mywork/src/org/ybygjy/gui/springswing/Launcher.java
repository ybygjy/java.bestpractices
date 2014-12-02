package org.ybygjy.gui.springswing;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 负责加载引导初始化Swing框架
 * @author WangYanCheng
 * @version 2011-2-16
 */
public class Launcher {
    /**
     * 负责加载spring相关配置
     */
    public void launch() {
        String[] contextPaths = new String[] {"org/ybygjy/gui/springswing/app-context.xml"};
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext(contextPaths);
    }
}
