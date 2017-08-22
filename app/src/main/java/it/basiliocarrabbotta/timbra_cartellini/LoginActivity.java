package it.basiliocarrabbotta.timbra_cartellini;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private LocationManager locationManager;
    private double longitudeGPS, latitudeGPS;
    private TextView longitudeValueGPS, latitudeValueGPS;
    private Button timbra_entrata, timbra_uscita, statistiche;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        locationManager = (LocationManager) getSystemService(getBaseContext().LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerGPS);


        longitudeValueGPS = (TextView) findViewById(R.id.longitudeValueGPS);
        latitudeValueGPS = (TextView) findViewById(R.id.latitudeValueGPS);
        timbra_entrata = (Button) findViewById(R.id.btnEntra);
        timbra_uscita = (Button) findViewById(R.id.btnEsci);
        statistiche = (Button) findViewById(R.id.btnStatiche);
        relativeLayout = (RelativeLayout) findViewById(R.id.rel);


        if (!isLocationEnabled())
            showAlert();




        ArrayList<Coordinates> CasaNonno = new ArrayList<>();

        Coordinates p1 = new Coordinates(38.266998, 15.596746);
        Coordinates p2 = new Coordinates(38.267150, 15.596779);
        Coordinates p3 = new Coordinates(38.267103, 15.597053);
        Coordinates p4 = new Coordinates(38.266958, 15.597013);
        Coordinates p5 = new Coordinates(38.266998, 15.596746);

        CasaNonno.add(p1);
        CasaNonno.add(p2);
        CasaNonno.add(p3);
        CasaNonno.add(p4);
        CasaNonno.add(p5);


        final Poligon Casanonno_pol = new Poligon(CasaNonno);

        Coordinates p6 = new Coordinates(38.267062, 15.596888);
        if (Casanonno_pol.InsidePolygon(p6))
            Log.d("GPS LOCATOR", "Sta dentro");
        else
            Log.d("GPS LOCATOR", "Molto male");

        Coordinates p7 = new Coordinates(38.267030, 15.596386);
        if (!Casanonno_pol.InsidePolygon(p7)) ;
        Log.d("GPS LOCATOR", " Test superati");

        timbra_entrata.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(),"Permission not granted",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Coordinates p8 = new Coordinates(latitudeGPS,longitudeGPS);
                    if(Casanonno_pol.InsidePolygon(p8))
                        Toast.makeText(getBaseContext(),"Sei dentro la casa del nonno", Toast.LENGTH_LONG).show();


                }}
        });
    }


    private boolean isLocationEnabled() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return true;
        else
            return false;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Il GPS non è attivo, attivarlo per utilizzare questa applicazione.")
                .setPositiveButton("IMPOSTAZIONI GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();


            longitudeValueGPS.setText(longitudeGPS + "");
            latitudeValueGPS.setText(latitudeGPS + "");
            Toast.makeText(LoginActivity.this, "GPS Provider update", Toast.LENGTH_SHORT).show();


        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }
        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };





}