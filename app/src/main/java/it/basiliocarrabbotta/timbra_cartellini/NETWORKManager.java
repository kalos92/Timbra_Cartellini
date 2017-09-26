package it.basiliocarrabbotta.timbra_cartellini;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kalos on 22/08/2017.
 */

public class NETWORKManager implements LocationListener, Subject {


    private double NETlat= 0.0d;
    private double NETlng= 0.0d;
    private static NETWORKManager INSTANCE = null;
    private ArrayList<RepositoryObserver> Observers;

    private NETWORKManager(){  //Con un Singleton ho la certezza che questa classe funzioni e venga creata una sola volta
        Observers= new ArrayList<>();
    }

    public static NETWORKManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new NETWORKManager();
        }
        return INSTANCE;
    }

    public static void DestroyNETMGR(){
        INSTANCE = null;
    }



    @Override
    public void onLocationChanged(Location location) {

        NETlng = location.getLongitude();
        NETlat = location.getLatitude();


         notifyObservers();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public double getNETlat()
    {
        return NETlat;
    }


    public double getNETlng()
    {
        return NETlng;
    }


    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if(!Observers.contains(repositoryObserver)) {
            Observers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        if(Observers.contains(repositoryObserver)) {
            Observers.remove(repositoryObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver observer: Observers) {
            observer.onUserDataChanged(NETlng, NETlat);
        }
    }


}
