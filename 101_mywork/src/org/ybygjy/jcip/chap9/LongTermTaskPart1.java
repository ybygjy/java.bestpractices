package org.ybygjy.jcip.chap9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Swing执行长时间任务的方式
 * <p>该类描述了Swing执行耗时任务的常用方式</p>
 * <p>1、利用SwingUtilities#invokeLater、invokeAndWait将对swing组件状态的访问封闭到事件线程</p>
 * <p>2、任务执行服务提供独立的后端线程用于执行耗时任务</p>
 * @author WangYanCheng
 * @version 2014年9月8日
 */
public class LongTermTaskPart1 {
	/** 负责执行任务的线程池*/
	private static ExecutorService backgroundExec = Executors.newCachedThreadPool();
	/**
	 * 测试入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		JFrame jframe = new JFrame("长时间任务");
		jframe.setSize(300, 300);
		final JButton startBtn = new JButton("StartBtn");
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog((JButton) e.getSource(), "HelloWorld");
				startBtn.setEnabled(false);
				startBtn.setText("Busy");
				backgroundExec.execute(new Runnable() {
					public void run() {
						int step = 1;
						while (true) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println(step);
							if (step % 20 == 0) {
								break;
							}
							step++;
						}
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								startBtn.setEnabled(true);
								startBtn.setText("complete");
							}
						});
					}
				});
			}
		});
		jframe.getContentPane().add(startBtn);
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
}
