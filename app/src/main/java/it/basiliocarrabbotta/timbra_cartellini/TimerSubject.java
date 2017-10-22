package it.basiliocarrabbotta.timbra_cartellini;

/**
 * Created by Kalos on 18/10/2017.
 */

public interface TimerSubject {
    void registerObserver(TimerObserver repositoryObserver);
    void removeObserver(TimerObserver repositoryObserver);
    void notifyObservers();
}
