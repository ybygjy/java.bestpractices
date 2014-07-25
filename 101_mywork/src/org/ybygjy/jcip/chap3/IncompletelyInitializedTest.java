package org.ybygjy.jcip.chap3;

/**
 * 未完全初始化
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
class Node {
    public Node(Leaker leaker) {
        leaker.leak(this);
    }
    protected String getName() {
        return "BaseNode";
    }
}
class NamingNode extends Node {
    private String nodeName;
    public NamingNode(Leaker leaker, String nodeName) {
        super(leaker);
        this.nodeName = nodeName;
    }
    public String getName() {
        return nodeName;
    }
}
class Leaker {
    public void leak(Node node) {
        System.out.println("node.getName()=>" + node.getName());
    }
}