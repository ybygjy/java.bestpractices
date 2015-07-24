package org.ybygjy.basic.algorithms.hash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性Hash算法
 * @author WangYanCheng
 * @version 2015年4月7日
 */
public class ConsistentHash {
	//虚拟节点
	private TreeMap virtualNodes;
	//真实节点
	private NodeConfig[] rawNodes;
	//各真实节点关联的虚拟节点个数
	private final int NODE_NUM = 5;
	//Hash函数
	private static HashMethod hashMethod = new MurMurHash();
	/**
	 * 构造函数
	 * @param nodes
	 */
	public ConsistentHash(NodeConfig[] nodes) {
		this.rawNodes = nodes;
		init();
	}
	/**
	 * 构造Hash环
	 */
	private void init() {
		this.virtualNodes = new TreeMap();
		for(int i = 0; i != this.rawNodes.length; i++) {
			final NodeConfig nodesCfg = this.rawNodes[i];
			for (int n = 0; n < NODE_NUM; n++) {
				this.virtualNodes.put(hashMethod.hash("SHARD-" + i + "-NODE-" + n), nodesCfg);
			}
		}
	}
	public Object get(String key) {
		Long hashValue = this.hashMethod.hash(key);
		SortedMap tmpSortMap = this.virtualNodes.tailMap(hashValue);
		NodeConfig nodeConfig = (NodeConfig) tmpSortMap.get(tmpSortMap.lastKey());
		if (null != nodeConfig) {
			//取值
		}
		return null;
	}
	@Override
	public String toString() {
		return "ConsistentHash [virtualNodes=" + virtualNodes + ", rawNodes=" + rawNodes + ", NODE_NUM=" + NODE_NUM + "]";
	}
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		//定义真实结点
		NodeConfig[] rawNodeConfig = new NodeConfig[2];
		rawNodeConfig[0] = (new NodeConfig("192.168.190.2", 8899, "RAWNode_001"));
		rawNodeConfig[1] = (new NodeConfig("192.168.190.2", 8899, "RAWNode_002"));
		ConsistentHash consistHash = new ConsistentHash(rawNodeConfig);
	}
}
/**
 * 配置节点
 * @author WangYanCheng
 * @version 2015年7月22日
 */
class NodeConfig {
	private String host;
	private int port;
	private String nodeSerial;
	
	public NodeConfig(String host, int port, String nodeSerial) {
		super();
		this.host = host;
		this.port = port;
		this.nodeSerial = nodeSerial;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getNodeSerial() {
		return nodeSerial;
	}
	public void setNodeSerial(String nodeSerial) {
		this.nodeSerial = nodeSerial;
	}
}
/**
 * 抽象不同的Hash计算方式
 * @author WangYanCheng
 * @version 2015年7月22日
 */
abstract class HashMethod {
	public Long hash(String key) {
		return Long.valueOf(key.hashCode());
	}
}
/**
 * MurMurHash一种实现Hash的算法
 * @author WangYanCheng
 * @version 2015年7月22日
 */
class MurMurHash extends HashMethod {
	/**
	 * MurMurHash算法
	 * @override
	 * @param key
	 * @return h
	 */
	public Long hash(String key) {
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
}
