package org.ybygjy.basic.algorithms.hash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 一致性Hash算法
 * @author WangYanCheng
 * @version 2015年4月7日
 */
public class ConsistentHash<T> {
	//虚拟节点
	private TreeMap<Long, T> virtualNodes;
	//真实节点
	private List<T> rawNodes;
	//各真实节点关联的虚拟节点个数
	private final int NODE_NUM = 5;
	/**
	 * 构造函数
	 * @param nodes
	 */
	public ConsistentHash(List<T> nodes) {
		this.rawNodes = nodes;
		init();
	}
	/**
	 * 构造Hash环
	 */
	private void init() {
		this.virtualNodes = new TreeMap<Long, T>();
		for(int i = 0; i != this.rawNodes.size(); ++i) {
			final T nodesCfg = this.rawNodes.get(i);
			for (int n = 0; n < NODE_NUM; n++) {
				this.virtualNodes.put(hash("SHARD-" + i + "-NODE-" + n), nodesCfg);
			}
		}
	}
	/**
	 * MurMurHash算法
	 * @param key
	 * @return h
	 */
	public static final Long hash(String key) {
		ByteBuffer buff = ByteBuffer.wrap(key.getBytes(Charset.forName("UTF-8")));
		int seed = 0x1234ABCD;
		ByteOrder byteOrder = buff.order();
		buff.order(ByteOrder.LITTLE_ENDIAN);
		long m = 0xc6a4a7935bd1e995L;
		int r = 47;
		long h = seed ^ (buff.remaining() * m);
		long k;
		while(buff.remaining() >= 8) {
			k = buff.getLong();
			
			k *= m;
			k ^= k >>> r;
			k *= m;
			
			h ^= k;
			h *= m;
		}
		if (buff.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
			//for big-endian version do this first
			//finish.position(8-buff.remaining())
			finish.put(buff).rewind();
			h ^= finish.getLong();
			h *= m;
		}
		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;
		buff.order(byteOrder);
		return h;
	}
	
	@Override
	public String toString() {
		return "ConsistentHash [virtualNodes=" + virtualNodes + ", rawNodes="
				+ rawNodes + ", NODE_NUM=" + NODE_NUM + "]";
	}
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		List<Object> nodes = new ArrayList<Object>();
		nodes.add(new Object());
		nodes.add(new Object());
		nodes.add(new Object());
		ConsistentHash<Object> consistentHashInst = new ConsistentHash<Object>(nodes);
		System.out.println(consistentHashInst);
	}
}
