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

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.

    private double longitudeGPS, latitudeGPS;
    private TextView longitudeValueGPS, latitudeValueGPS;
    private Button timbra_entrata, timbra_uscita, statistiche;
    private SessionManager manager;
    private CoordinatorLayout coordinatorLayout;
    private int time;
    private TextClock textClock;
    private EditText username,pwd;
    private String ID;
    private Boolean id_setted = false;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extra = getIntent().getExtras();
        if(extra!= null) {

            latitudeGPS = (double) extra.get("Lat");
            longitudeGPS = (double) extra.get("Lng");

        }

        manager = new SessionManager(getBaseContext());
        timbra_entrata = (Button) findViewById(R.id.btnEntra);
        timbra_uscita = (Button) findViewById(R.id.btnEsci);
        statistiche = (Button) findViewById(R.id.btnStatiche);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        username = (EditText) findViewById(R.id.editext_username);
        pwd = (EditText) findViewById(R.id.editext_password);
        textClock = (TextClock) findViewById(R.id.clock);

        ID = manager.getID();

        id_setted = ID != null? true:false;

        longitudeValueGPS.setText(String.valueOf(longitudeGPS));
        latitudeValueGPS.setText(String.valueOf(latitudeGPS));

        timbra_entrata.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("") || pwd.getText().toString().equals("")){
                    Snackbar.make(coordinatorLayout,"Non hai inserito i dati richiesti",Snackbar.LENGTH_LONG).show();
                    return;
                }


                bundle= new Bundle();
                bundle.putBoolean("ID_SETTED",id_setted);
                bundle.putDouble("LAT",latitudeGPS);
                bundle.putDouble("LNG",longitudeGPS);
                bundle.putString("USERNAME",username.getText().toString());
                bundle.putString("PASSWORD",pwd.getText().toString());
                bundle.putString("TIPO","Entrata");
                Intent i = new Intent(LoginActivity.this,Causali.class);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });

        timbra_uscita.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("") || pwd.getText().toString().equals("")){
                    Snackbar.make(coordinatorLayout,"Non hai inserito i dati richiesti",Snackbar.LENGTH_LONG).show();
                    return;
                }


                bundle= new Bundle();
                bundle.putBoolean("ID_SETTED",id_setted);
                bundle.putDouble("LAT",latitudeGPS);
                bundle.putDouble("LNG",longitudeGPS);
                bundle.putString("USERNAME",username.getText().toString());
                bundle.putString("PASSWORD",pwd.getText().toString());
                bundle.putString("TIPO","Uscita");
                Intent i = new Intent(LoginActivity.this,Causali.class);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });
        statistiche.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("") || pwd.getText().toString().equals("")){
                    Snackbar.make(coordinatorLayout,"Non hai inserito i dati richiesti",Snackbar.LENGTH_LONG).show();
                    return;
                }


                bundle= new Bundle();
                bundle.putBoolean("ID_SETTED",id_setted);
                bundle.putDouble("LAT",latitudeGPS);
                bundle.putDouble("LNG",longitudeGPS);
                bundle.putString("USERNAME",username.getText().toString());
                bundle.putString("PASSWORD",pwd.getText().toString());
                bundle.putString("TIPO","Entrata");
                Intent i = new Intent(LoginActivity.this,Report.class);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });



    }











}