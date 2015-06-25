package org.ybygjy.basic;

/**
 * 在读一些开源类库源码的时候发现了一种处理主入口通过notify机制处理启动多个线程的处理任务的情况
 * @author WangYanCheng
 * @version 2015年6月25日
 */
public class MainClassControl {
	private static volatile boolean running = true;
	public static void main(String[] args) {
		//创建工作线程组
		new Thread(new Runnable(){
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//发起退出
				System.exit(0);
			}
		}).start();
		//注册程序退出回调
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				try {
					System.out.println("程序退出释放资源!");
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized(MainClassControl.class) {
					running = false;
					MainClassControl.class.notify();
				}
			}
		});
		//等待
		synchronized(MainClassControl.class) {
			while(running) {
				System.out.println("等待退出!");
				try {
					MainClassControl.class.wait();
					System.out.println("退出了!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
