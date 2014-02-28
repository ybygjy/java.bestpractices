package org.ybygjy.pattern.flyweight.simple;
/**
 * TestUser Flyweight pattern
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class Client {
    /**
     * debug portal
     * @param args arguments lists
     */
    public static void main(String[] args) {
        FlyweightFactory flyweightFact = FlyweightFactory.getInstance();
        AbstractFlyweight afInst = flyweightFact.doGetFlyweight('A');
        afInst.operation("Call A.");
        afInst = flyweightFact.doGetFlyweight('B');
        afInst.operation("Call B.");
        afInst = flyweightFact.doGetFlyweight('A');
        afInst.operation("Second Call A");
        flyweightFact.doCheckFlyweight();
    }
}
