package it.basiliocarrabbotta.timbra_cartellini;

/**
 * Created by Kalos on 09/09/2017.
 */

public interface Subject {

    void registerObserver(RepositoryObserver repositoryObserver);
    void removeObserver(RepositoryObserver repositoryObserver);
    void notifyObservers();
}
