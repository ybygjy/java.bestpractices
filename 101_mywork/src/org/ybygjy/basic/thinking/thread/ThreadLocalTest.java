package org.ybygjy.basic.thinking.thread;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程内部非共享资源
 * @author WangYanCheng
 * @version 2013-1-10
 */
public class ThreadLocalTest {
    private ResourceMgr resourceMgr;
    public ThreadLocalTest() {
        resourceMgr = new ResourceMgr();
    }
    public void doWork() {
        new InnerThread("Thread-0001", resourceMgr).start();
        //new InnerThread("Thread-0002", resourceMgr).start();
    }
    public static void main(String[] args) {
        new ThreadLocalTest().doWork();
    }
}
class ResourceMgr {
    private ThreadLocal<Map<String, String>> sources = new ThreadLocal<Map<String, String>>(){
        @Override
        protected Map<String, String> initialValue() {
            return new HashMap<String, String>();
        }
    };
    public void put(String key, String value) {
        sources.get().put(key, value);
    }
    public String get(String key) {
        return sources.get().get(key);
    }
    public ThreadLocal<Map<String, String>> getThreadLocal() {
        return sources;
    }
}
class InnerThread extends Thread {
    private static ResourceMgr resourceMgr;
    public InnerThread(String threadName, ResourceMgr resourceMgr) {
        this.resourceMgr = resourceMgr;
    }
    public void run() {
        while (true) {
            //存值
            resourceMgr.put(getName(), String.valueOf(Math.random()*10));
            System.out.println(resourceMgr.getThreadLocal().toString());
            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //取值
            System.out.println(getName().concat("取值-->").concat(resourceMgr.get(getName())));
        }
    }
}
