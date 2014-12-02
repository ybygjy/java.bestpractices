package org.ybygjy.jcip.chap3;

/**
 * 未完全初始化
 * 1.首先采用了继承机制
 * 2.父类和子类都有getName方法
 * 3.子类override父类getName方法
 * 4.子类带参构造函数调用父类带参构造函数
 * 5.最重要的一点就是，类实例在进行初始化过程中被外部其它实例访问，导致外部实例产生非正常状态
 * @author WangYanCheng
 * @version 2014-7-22
 */
public class IncompletelyInitializedTest {
    public static void main(String[] args) {
        Leaker leaker = new Leaker();
        Node node = new NamingNode(leaker, "Hello World!");
        System.out.println(node.getName());
    }
}
/**
 * 父类
 */
class Node {
	private String nodeName;
	/**
	 * 构造函数
	 * @param leaker {@link Leaker}
	 */
    public Node(Leaker leaker) {
        leaker.leak(this);
    }
    /**
     * 构造函数
     * @param nodeName nodeName
     */
    public Node(String nodeName) {
    	this.nodeName = nodeName;
    }
    /**
     * 可被子类覆盖的方法
     * @return nameStr
     */
    protected String getName() {
        return "BaseNode";
    }
}
/**
 * 子类
 */
class NamingNode extends Node {
    private String nodeName;
    /**
     * 构造函数
     * @param leaker {@link Leaker}
     * @param nodeName nameStr
     */
    public NamingNode(Leaker leaker, String nodeName) {
        super(leaker);
        this.nodeName = nodeName;
    }
    @Override
    public String getName() {
        return nodeName;
    }
}
/**
 * 该类实例负责在{@link NamingNode}未完全初始化过程中访问{@link NamingNode}实例
 * @author WangYanCheng
 * @version 2014-07-22
 */
class Leaker {
    public void leak(Node node) {
        System.out.println("node.getName()=>" + node.getName());
    }
}