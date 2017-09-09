package it.basiliocarrabbotta.timbra_cartellini;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Kalos on 22/08/2017.
 */

public class NETWORKManager implements LocationListener {


    private double NETlat= 0.0d;
    private double NETlng= 0.0d;
    private SplashScreen sp;

    public NETWORKManager(SplashScreen sp){

        this.sp=sp;
    }

    @Override
    public void onLocationChanged(Location location) {

        NETlng = location.getLongitude();
        NETlat = location.getLatitude();

        Toast.makeText(sp, String.valueOf(NETlat)+String.valueOf( NETlng), Toast.LENGTH_SHORT).show();

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



}
