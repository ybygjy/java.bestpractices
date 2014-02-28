package org.ybygjy.pattern.complex.simduck;


public interface QuackObservable {
    public void registerObserver(Observer observer);
    public void notifyObservers();
}
