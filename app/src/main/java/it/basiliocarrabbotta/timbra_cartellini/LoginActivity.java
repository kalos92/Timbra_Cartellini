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
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnMapReadyCallback {


    // UI references.

    private double longitudeGPS, latitudeGPS;
    private SessionManager manager;
    private CoordinatorLayout coordinatorLayout;
    private int time;
    private TextClock textClock;
    private String ID;
    private Boolean id_setted = false;
    private Bundle bundle;
    private SQLiteHandler SQL;
    private boolean logged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Bundle extra = getIntent().getExtras();
        if(extra!= null) {

            latitudeGPS = (double) extra.get("Lat");
            longitudeGPS = (double) extra.get("Lng");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SQL = new SQLiteHandler(getBaseContext());
        manager = new SessionManager(getBaseContext());
        Button timbra_entrata = (Button) findViewById(R.id.btnEntra);
        Button timbra_uscita = (Button) findViewById(R.id.btnEsci);
        Button statistiche = (Button) findViewById(R.id.btnStatiche);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        textClock = (TextClock) findViewById(R.id.clock);

        TextView longitudeValueGPS = (TextView) findViewById(R.id.longitudeValueGPS);
        TextView latitudeValueGPS = (TextView) findViewById(R.id.latitudeValueGPS);

        ID = manager.getID();
        longitudeValueGPS.setText(String.valueOf(longitudeGPS));
        latitudeValueGPS.setText(String.valueOf(latitudeGPS));

        timbra_entrata.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                    bundle= new Bundle();
                    bundle.putBoolean("ID_SETTED",id_setted);
                    bundle.putDouble("LAT",latitudeGPS);
                    bundle.putDouble("LNG",longitudeGPS);
                    bundle.putString("USERNAME",SQL.getUserDetails(ID).get("username"));
                    bundle.putString("PASSWORD",SQL.getUserDetails(ID).get("password"));
                    bundle.putString("TIPO","Entrata");
                    bundle.putString("CLOCK",textClock.toString());
                    bundle.putLong("CLOCK_SECOND",manager.getTimeinsec());

                    Intent i = new Intent(LoginActivity.this,Causali.class);
                    i.putExtras(bundle);
                    startActivity(i);
                   }

        });

        timbra_uscita.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                    bundle= new Bundle();
                    bundle.putBoolean("ID_SETTED",id_setted);
                    bundle.putDouble("LAT",latitudeGPS);
                    bundle.putDouble("LNG",longitudeGPS);
                    bundle.putString("USERNAME",SQL.getUserDetails(ID).get("username"));
                    bundle.putString("PASSWORD",SQL.getUserDetails(ID).get("password"));
                    bundle.putString("TIPO","Uscita");
                    bundle.putString("CLOCK",textClock.toString());
                    bundle.putLong("CLOCK_SECOND",manager.getTimeinsec());

                    Intent i = new Intent(LoginActivity.this,Causali.class);
                    i.putExtras(bundle);
                    startActivity(i);

            }
        });
        statistiche.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this,SplashScreen.class);
                startActivity(i);
                //Da quiik

            }
        });



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng( latitudeGPS,longitudeGPS);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Ti trovi circa qui"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17.5f));

    }
}