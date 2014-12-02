package org.ybygjy.pattern.memento.c02;

/**
 * Memento Pattern客户端，测试与验证入口
 * @author WangYanCheng
 * @version 2013-1-7
 */
public class Client {
    public static void main(String[] args) {
        //负责人
        Caretaker caretaker = new Caretaker();
        //发起人
        Originator originator = new Originator();
        originator.changeState("ON");
System.out.println(originator.toString());
        //转储状态
        caretaker.saveMemento(originator.createMemenIF());
        originator.changeState("OFF");
System.out.println(originator.toString());
        //恢复状态
        originator.restoreMemento(caretaker.retrieveMemento());
System.out.println(originator.toString());
    }
}
