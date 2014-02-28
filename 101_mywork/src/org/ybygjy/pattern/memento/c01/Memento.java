package org.ybygjy.pattern.memento.c01;

public class Memento {
    private String state;
    public Memento(String state) {
        this.state = state;
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
    }
    
}
