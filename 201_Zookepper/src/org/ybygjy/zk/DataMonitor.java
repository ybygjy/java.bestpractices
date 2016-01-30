package org.ybygjy.zk;

import java.util.Arrays;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 监听zk结点状态
 * @author WangYanCheng
 * @version 2016年1月4日
 */
public class DataMonitor implements Watcher, StatCallback {
	private ZooKeeper zk;
	private String zNode;
	private Watcher chainedWatcher;
	private volatile boolean dead;
	private DataMonitorListener listener;
	private byte[] prevData;
	public DataMonitor(ZooKeeper zk, String zNode, Watcher chainedWatcher, DataMonitorListener listener) {
		this.zk = zk;
		this.zNode = zNode;
		this.chainedWatcher = chainedWatcher;
		this.listener = listener;
		zk.exists(this.zNode, true, this, null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.apache.zookeeper.AsyncCallback.StatCallback#processResult(int, java.lang.String, java.lang.Object, org.apache.zookeeper.data.Stat)
	 */
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		boolean exists;
		switch(rc) {
		case Code.Ok:
			exists = true;
			break;
		case Code.NoNode:
			exists = false;
			break;
		case Code.SessionExpired:
		case Code.NoAuth:
			this.dead = true;
			this.listener.closing(rc);
			return;
		default:
			zk.exists(this.zNode, true, this, null);
			return;
		}
		byte[] bytes = null;
		if (exists) {
			try {
				bytes = zk.getData(this.zNode, false, null);
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				return;
			}
		}
		if ((bytes == null && bytes != this.prevData) || (bytes != null && !Arrays.equals(this.prevData, bytes))) {
			this.listener.exists(bytes);
			this.prevData = bytes;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
	 */
	public void process(WatchedEvent event) {
		String path = event.getPath();
		if (event.getType() == Event.EventType.None) {
			switch(event.getState()) {
			case SyncConnected:
				break;
			case Expired:
				dead = true;
				this.listener.closing(KeeperException.Code.SessionExpired);
				break;
			default:
				break;
			}
		} else {
			if (path != null && path.equals(this.zNode)) {
				this.zk.exists(this.zNode, true, this, null);
			}
		}
		if (this.chainedWatcher != null) {
			this.chainedWatcher.process(event);
		}
	}
	public boolean isDead() {
		return this.dead;
	}
	public interface DataMonitorListener {
		/**
		 * Node结点状态发生变化
		 * @param data
		 */
		public void exists(byte[] data);
		/**
		 * zk session is no longer valid
		 * @param rc
		 */
		public void closing(int rc);
	}
}
