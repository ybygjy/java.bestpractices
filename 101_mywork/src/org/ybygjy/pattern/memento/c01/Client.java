package org.ybygjy.pattern.memento.c01;

public class Client {
    private Originator o;
    private Caretaker c;
    public void doWork() {
        o = new Originator();
        c = new Caretaker();
        o.setState("On");
        c.saveMemento(o.createMemento());
        o.setState("Off");
        o.restoreMemento(c.retrieveMemento());
    }
    public static void main(String[] args) {
        new Client().doWork();
    }
}
