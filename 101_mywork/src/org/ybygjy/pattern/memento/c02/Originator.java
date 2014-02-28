package org.ybygjy.pattern.memento.c02;

/**
 * 发起人角色
 * <p>
 * 1、拥有对Memento角色的完全控制(宽接口)
 * <p>
 * 2、对外界提供Memento角色的小权限访问(窄接口)
 * @author WangYanCheng
 * @version 2013-1-7
 */
public class Originator {
    private String state;

    public MementoIF createMemenIF() {
        return new Memento(state);
    }

    public void restoreMemento(MementoIF memento) {
        if (memento instanceof Memento) {
            changeState((Memento) memento);
        }
    }
    public void changeState(String state) {
        this.state = state;
    }
    protected void changeState(Memento memento) {
        this.state = memento.getState();
    }
    @Override
    public String toString() {
        return "Originator [state=" + state + "]";
    }

    private class Memento implements MementoIF {
        private String state;

        public Memento(String state) {
            this.state = state;
        }

        /**
         * @return the state
         */
        private String getState() {
            return state;
        }

        /**
         * @param state the state to set
         */
        private void setState(String state) {
            this.state = state;
        }
    }
}
