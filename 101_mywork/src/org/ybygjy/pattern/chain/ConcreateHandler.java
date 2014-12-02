package org.ybygjy.pattern.chain;

public class ConcreateHandler implements Handler {
    private String handlerName;
    private Handler successor;
    public ConcreateHandler(String handlerName, Handler successor) {
        this.handlerName = handlerName;
        this.setSuccessor(successor);
    }
    public void handleRequest() {
        System.out.println("处理请求==>".concat(handlerName));
        if (this.getSuccessor() != null) {
            System.out.println("The request is passed to ".concat(this.getSuccessor().toString()));
            successor.handleRequest();
        }
    }

    public void setSuccessor(Handler handler) {
        this.successor = handler;
    }

    public Handler getSuccessor() {
        return this.successor;
    }
}
