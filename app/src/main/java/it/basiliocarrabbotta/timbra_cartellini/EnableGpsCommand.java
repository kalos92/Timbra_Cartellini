package it.basiliocarrabbotta.timbra_cartellini;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * Created by Kalos on 22/08/2017.
 */

public class EnableGpsCommand extends CancelCommand
{
    public EnableGpsCommand(SplashScreen activity, AlertDialog.Builder dialog, LocationManager locationManager) {
        super(activity,dialog, locationManager);
    }

    public Criteria execute()
    {
        // take the user to the phone gps settings and then start the asyncronous logic.
        m_activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        m_activity.setCriteria(super.execute());
        return null;

    }
}
