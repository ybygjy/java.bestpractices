package org.ybygjy.pattern.chain;

public class Main {
    public static void main(String[] args) {
        Handler handlerB = new ConcreateHandler("HandlerB", null);
        Handler handlerA = new ConcreateHandler("HandlerA", handlerB);
        handlerA.handleRequest();
    }
}
