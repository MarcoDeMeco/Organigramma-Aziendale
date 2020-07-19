package Model;

import View.Observer;

import java.util.LinkedList;

public abstract class AbstractModel {
    LinkedList<Observer> observers = new LinkedList<>();

    void attach(Observer observer){
        if (observers.contains(observer)) {
            return;
        }
        observers.add(observer);
    }

    void detach(Observer observer){
        observers.remove(observer);
    }

    void aggiorna(){
        for (Observer observer : observers) {
            observer.aggiorna();
        }
    }
}
