package org.ybygjy.pattern.complex.simduck.impl;

import org.ybygjy.pattern.complex.simduck.Observable;
import org.ybygjy.pattern.complex.simduck.Observer;
import org.ybygjy.pattern.complex.simduck.Quackable;

public class DuckCall implements Quackable {
    private Observable observable;
    public DuckCall() {
        this.observable = new Observable(this);
    }
    public void quack() {
        System.out.println("DuckCall..");
        this.notifyObservers();
    }

    public void notifyObservers() {
        this.observable.notifyObservers();
    }

    public void registerObserver(Observer observer) {
        this.observable.registerObserver(observer);
    }
}
