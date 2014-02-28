package org.ybygjy.pattern.flyweight.complex;
/**
 * 负责对复合享元模式的测试
 * @author WangYanCheng
 * @version 2010-11-22
 */
public class Client {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        FlyweightFactory ffInst = FlyweightFactory.getInstance();
        AbstractFlyweight afInst = ffInst.flyweight('A');
        afInst.doSomething("Instrinstic State A");
        ffInst.flyweight('A').doSomething("Instrinstic State A");
        afInst = ffInst.flyweight("ABCDEFABCDEF");
        afInst.doSomething("Composite Flyweight");
    }
}
