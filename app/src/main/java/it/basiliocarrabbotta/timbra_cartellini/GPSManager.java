package it.basiliocarrabbotta.timbra_cartellini;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Kalos on 14/08/2017.
 */

public class GPSManager implements LocationListener {

    double GPSlat= 0.0d;
    double GPSlng= 0.0d;



    @Override
    public void onLocationChanged(Location location) {

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


    public double getGPSlat()
    {
        return GPSlat;
    }


    public double getGPSlng()
    {
        return GPSlng;
    }


}
