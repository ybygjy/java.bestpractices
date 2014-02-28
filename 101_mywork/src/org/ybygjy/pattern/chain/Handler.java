package org.ybygjy.pattern.chain;

public interface Handler {
    public void handleRequest();
    public void setSuccessor(Handler handler);
}
