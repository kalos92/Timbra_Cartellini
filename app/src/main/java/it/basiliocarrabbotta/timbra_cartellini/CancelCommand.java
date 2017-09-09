package it.basiliocarrabbotta.timbra_cartellini;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Kalos on 22/08/2017.
 */

public class CancelCommand implements ICommand {

    protected final LocationManager locationManager;
    protected   SplashScreen m_activity;
    protected AlertDialog.Builder dialog;
    protected    String provider;

    public CancelCommand(SplashScreen activity, AlertDialog.Builder dialog, LocationManager locationManager)
    {
        m_activity = activity;
        this.dialog=dialog;
        this.locationManager = locationManager;
    }

    public Criteria execute()
    {

        //start asyncronous operation here
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        provider = locationManager.getBestProvider(criteria, true);

        if (provider == null){

            Log.d("LOCATOR" , "No connessione");
        }

        else if(provider.equals(LocationManager.GPS_PROVIDER)) {
            Log.d("LOCATOR" ,provider);

        }

        else if (provider.equals(LocationManager.NETWORK_PROVIDER)){

            Log.d("LOCATOR" ,provider);
        }

        else if (provider.equals(LocationManager.PASSIVE_PROVIDER)) {
            Log.d("LOCATOR" ,provider);
        }

        return criteria;
    }
}

