package org.ybygjy.jcip.chap5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 中断与阻塞
 * @author WangYanCheng
 * @version 2015年4月26日
 */
public class InterruptAndBlockingTest implements ActionListener {
	//中断标记
	private transient boolean isInterrupt = false;
	/**
	 * 任务入口
	 */
	public void doWork() {
		//如何中断线程呢？首先线程必须是可中断的，当线程在sleep、wait或主动检测中断状态时可以被中断
		final Thread workThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println("线程" + Thread.currentThread().getName() + "正在等待中断。。。");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		final Thread ctrlThread = new Thread(new Runnable() {
			private Thread wt = workThread;
			public void run() {
				while (true) {
					if (InterruptAndBlockingTest.this.isInterrupt) {
						this.wt.interrupt();
					}
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		workThread.start();
		ctrlThread.start();
	}
	/**
	 * 测试入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					InterruptAndBlockingTest iabtInst = new InterruptAndBlockingTest();
					JButton jbutton = new JButton();
					jbutton.setText("中断");
					jbutton.addActionListener(iabtInst);
					JFrame jframe = new JFrame();
					jframe.setDefaultCloseOperation(jframe.getDefaultCloseOperation());
					jframe.getContentPane().add(jbutton);
					jframe.setSize(300, 300);
					jframe.setVisible(true);
					iabtInst.doWork();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
		this.isInterrupt = true;
	}
}
