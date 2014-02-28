package org.ybygjy.pattern.memento.c01;

public class Originator {
    private String state;
    public Memento createMemento() {
        return new Memento(state);
    }
    public void restoreMemento(Memento memento) {
        this.setState(memento.getState());
    }
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
        System.out.println("Current sate=".concat(this.state));
    }
    
}
