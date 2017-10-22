package it.basiliocarrabbotta.timbra_cartellini;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class SplashScreen extends AppCompatActivity implements RepositoryObserver {

    public static final String TAG = SplashScreen.class.getSimpleName();
    private static final int PERMISSION_ACCESS_LOCATION = 144;
    private CoordinatorLayout cLayout;
    private LocationManager locationManager;
    private String provider;
    private NETWORKManager NETmgr;
    private static final int LONG_DELAY = 3500;
    private TextView dot1,dot2,dot3;
    private RequestQueue requestQueue;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash_screen);
        cLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        locationManager = (LocationManager) getSystemService(getBaseContext().LOCATION_SERVICE);
        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        dot1 = (TextView) findViewById(R.id.dot1);
        dot2 = (TextView) findViewById(R.id.dot2);
        dot3 = (TextView) findViewById(R.id.dot3);

        AlphaAnimation animation = new AlphaAnimation(0.0f,1.5f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        dot1.setAlpha(1f);
        dot2.setAlpha(1f);
        dot3.setAlpha(1f);

        AlphaAnimation animation2 = new AlphaAnimation(0.0f,1.5f);
        animation2.setDuration(2000);
        animation2.setRepeatCount(Animation.INFINITE);

        AlphaAnimation animation3= new AlphaAnimation(0.0f,1.5f);
        animation3.setDuration(2000);
        animation3.setRepeatCount(Animation.INFINITE);


        Timer timer_for_anim_1 = new Timer(1, 1, null,dot1,animation);
        Timer timer_for_anim_2 = new Timer(500, 1, null,dot2,animation2);
        Timer timer_for_anim_3 = new Timer(1000, 1, null,dot3,animation3);
        timer_for_anim_1.start();
        timer_for_anim_2.start();
        timer_for_anim_3.start();


        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {


            NETmgr = NETWORKManager.getInstance();
            NETmgr.registerObserver(this);
            provider = LocationManager.NETWORK_PROVIDER;


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_LOCATION);

            } else
                locationManager.requestLocationUpdates(provider, 0, 0, NETmgr);

        }

        else{
            Snackbar.make(cLayout,"Nessuna connessione a internet disponibile.\nL'app verrà chiusa tra poco",Snackbar.LENGTH_LONG).show();
            Timer timer = new Timer(LONG_DELAY, 10,this,null,null);
            timer.start();
        }



}


    @Override
    public void onUserDataChanged(double NETlng, double NETlat) {
        Toast.makeText(this,"HO OSSERVATO IL CAMBIAMENTO LNG: " +String.valueOf(NETlng)+" LAT: "+ String.valueOf(NETlat), Toast.LENGTH_LONG ).show();
        Log.d("COORDINATE", String.valueOf(NETlng)+" " +String.valueOf( NETlat));
        locationManager.removeUpdates(NETmgr);
        Intent data = new Intent(SplashScreen.this,LoginActivity.class);
        data.putExtra("Lat",NETlat);
        data.putExtra("Lng",NETlng);
        startActivity(data);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Snackbar.make(cLayout,"Attendi mentre trovo la tua posizione",Snackbar.LENGTH_LONG).show();
                    locationManager.requestLocationUpdates(provider, 0, 0, NETmgr);

                } else {

                    Snackbar.make(cLayout,"L'applicazione non può funzionare senza il tuo permesso, tra qualche secondo l'app verrà chiusa",Snackbar.LENGTH_LONG).show();
                    Timer timer = new Timer(LONG_DELAY, 1,this,null,null);
                    timer.start();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }





    }


//LONGITUDINE X
//LATITUDINE Y

//STANDARD GOOGLE = NETlat + NETlng

//45.0189677 - 7.6143349