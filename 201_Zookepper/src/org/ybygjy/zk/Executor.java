package org.ybygjy.zk;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 负责监听zk服务，与zk服务交互通信完成软负载感知
 * @author WangYanCheng
 * @version 2016年1月4日
 */
public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {
	private String znode;
	private DataMonitor dm;
	private ZooKeeper zk;
	private String fileName;
	private String[] exec;
	private Process child;
	/**
	 * Construction
	 * @param hostPort
	 * @param znode
	 * @param fileName
	 * @param exec
	 * @throws KeeperException
	 * @throws IOException
	 */
	public Executor(String hostPort, String znode, String fileName, String[] exec) throws KeeperException, IOException {
		this.fileName = fileName;
		this.exec = exec;
		this.znode = znode;
		this.zk = new ZooKeeper(hostPort, 3000, this);
		this.dm = new DataMonitor(this.zk, this.znode, null, this);
	}
	/**
	 * 测试入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		String hostPort = "127.0.0.1:2181";
		String zNode = "/zk_test";
		String fileName = "/Users/MLS/zk.log";
		String[] exec = {"ls", "-al", "."};
		try {
			new Executor(hostPort, zNode, fileName, exec).run();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void exists(byte[] data) {
		if (data == null) {
			if (child != null) {
				System.out.println("Killing process.");
				child.destroy();
				try {
					child.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			child = null;
		} else {
			if (child != null) {
				System.out.println("Stopping child.");
				child.destroy();
				try {
					child.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				FileOutputStream fous = new FileOutputStream(this.fileName, true);
				fous.write(data);
				fous.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				System.out.println("Starting child.");
				child = Runtime.getRuntime().exec(this.exec);
				new StreamWriter(child.getInputStream(), System.out);
				new StreamWriter(child.getErrorStream(), System.err);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void closing(int rc) {
		synchronized(this) {
			notifyAll();
		}
	}

	public void run() {
		try {
			synchronized(this) {
				while (!this.dm.isDead()) {
					wait();
				}
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void process(WatchedEvent event) {
		this.dm.process(event);
	}
	static class StreamWriter extends Thread {
		private OutputStream ousInst;
		private InputStream insInst;
		public StreamWriter(InputStream ins, OutputStream ous) {
			this.insInst = ins;
			this.ousInst = ous;
			start();
		}
		public void run() {
			byte[] bytes = new byte[80];
			int rc;
			try {
				while ((rc = this.insInst.read(bytes)) > 0) {
					this.ousInst.write(bytes, 0, rc);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
