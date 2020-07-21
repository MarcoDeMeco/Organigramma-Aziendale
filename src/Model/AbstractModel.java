package Model;

import View.Observer;

import java.util.LinkedList;

public abstract class AbstractModel {
    LinkedList<Observer> observers = new LinkedList<>();

    public void attach(Observer observer){
        if (observers.contains(observer)) {
            return;
        }
        observers.add(observer);
    }

    public void detach(Observer observer){
        observers.remove(observer);
    }

    protected void aggiorna(){
        for (Observer observer : observers) {
            observer.aggiorna();
        }
    }
}
