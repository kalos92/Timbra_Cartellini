package it.basiliocarrabbotta.timbra_cartellini;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

public class SplashScreen extends AppCompatActivity {


    private CoordinatorLayout cLayout;
    private LocationManager locationManager;
    private String provider;
    private Criteria criteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash_screen);
        cLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        locationManager = (LocationManager) getSystemService(getBaseContext().LOCATION_SERVICE);


        if (!EnableGPSIfPossible()){

            if (criteria != null) {
                provider = locationManager.getBestProvider(criteria, true);
            }

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            provider = locationManager.getBestProvider(criteria, true);
            Toast.makeText(getBaseContext(), provider, Toast.LENGTH_SHORT).show();
        } else {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            provider = locationManager.getBestProvider(criteria, true);
            Toast.makeText(getBaseContext(), provider, Toast.LENGTH_SHORT).show();
        }

        NETWORKManager NETmgr = new NETWORKManager(this);

        if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 0, 0, NETmgr);



                if(NETmgr.getNETlat()!=0.0d && NETmgr.getNETlng()!=0.0d)
                    Toast.makeText(this, "NELL'activity ho "+ String.valueOf(NETmgr.getNETlat()) + String.valueOf(NETmgr.getNETlng()),Toast.LENGTH_SHORT).show();









        }






    }


    private boolean isLocationEnabled() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return true;
        else
            return false;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Migliorare la posizione")
                .setMessage("Se non si è all'interno di un palazzo, attivare il GPS per risultati migliori")
                .setPositiveButton("IMPOSTAZIONI GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                        ShowAMessage(1);


                    }
                })
                .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        ShowAMessage(2);
                    }
                });
        dialog.show();

    }

    private boolean EnableGPSIfPossible()
    {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return true;
        }
        return false;
    }


    private void buildAlertMessageNoGps()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se non sei all'interno di un edificio, vuoi attivare il GPS per avere una precisione maggiore?")
                .setCancelable(false)
                .setPositiveButton("Sì", new CommandWrapper(new EnableGpsCommand(this,builder,locationManager)))
                .setNegativeButton("No", new CommandWrapper(new CancelCommand(this,builder,locationManager)));

        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void ShowAMessage(int tipo){

        switch (tipo){

            case 1:
                Snackbar.make(cLayout,"Se disponibile la posizione sarà ottenuta tramite GPS",Snackbar.LENGTH_LONG).show();
                break;
            case 2:
                Snackbar.make(cLayout,"La posizione registrata non avrà la massima precisione possibile",Snackbar.LENGTH_LONG).show();
                break;
            case 3:
                Snackbar.make(cLayout,"Non è possibile stabilire una connessione",Snackbar.LENGTH_LONG).show();
                break;
            case 4:
                Snackbar.make(cLayout,"La posizione è stata ottenuta con la massima precisione del GPS",Snackbar.LENGTH_LONG).show();
                break;
            case 5:
                Snackbar.make(cLayout,"La posizione è stata ottenuta con la precisione della rete Internet",Snackbar.LENGTH_LONG).show();
                break;
            case 6:
                Snackbar.make(cLayout,"La posizione è stata ottenuta con la minima precisione possibile, attivare una connessione per migliori risultati",Snackbar.LENGTH_LONG).show();
                break;





        }


    }

    public void setCriteria(Criteria criteria){
        this.criteria=criteria;
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        provider = locationManager.getBestProvider(criteria, true);
        Log.d("GUARDAMI", provider);




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){

            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            provider = locationManager.getBestProvider(criteria, true);
            provider = locationManager.getBestProvider(criteria, true);
            Toast.makeText(getBaseContext(), provider, Toast.LENGTH_SHORT).show();
        }
    }
}
