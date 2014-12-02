package org.ybygjy;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * 测试运行管理器
 * @author WangYanCheng
 * @version 2010-12-8
 */
public class TestRunner extends Runner {

    public TestRunner(Class obj) {
        System.out.println(obj);
    }
    @Override
    public Description getDescription() {
        System.out.println("Hello Description");
        return null;
    }

    @Override
    public void run(RunNotifier notifier) {
        System.out.println("Over" + notifier);
        notifier.fireTestRunStarted(null);
    }

}
