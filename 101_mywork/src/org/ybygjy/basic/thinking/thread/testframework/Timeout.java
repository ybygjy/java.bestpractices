package org.ybygjy.basic.thinking.thread.testframework;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器
 * @author WangYanCheng
 * @version 2010-9-30
 */
public class Timeout extends Timer {
    /**
     * Constructor
     * @param delay 延时
     * @param msg 消息
     */
    public Timeout(int delay, final String msg) {
        super(true); // daemon thread
        schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(msg);
                System.exit(0);
            }
        }, delay);
    }
}
