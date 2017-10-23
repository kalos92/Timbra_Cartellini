package it.basiliocarrabbotta.timbra_cartellini;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Configure_Activity extends AppCompatActivity {
    private EditText username,pwd;
    private Button btnLogin;
    private CoordinatorLayout coordinatorLayout;
    private ProgressDialog pDialog;
    private SessionManager manager;
    private SQLiteHandler SQL;
    private String UCode;
    private String hash;
    private int MY_PERMISSIONS_REQUEST_READ_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_);
        manager = new SessionManager(getBaseContext());

        if(manager.getID()!=null){
            Intent i = new Intent(Configure_Activity.this,SplashScreen.class);
            startActivity(i);
        }
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Configure_Activity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Configure_Activity.this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(Configure_Activity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        SQL = new SQLiteHandler(getBaseContext());
        username = (EditText) findViewById(R.id.editext_username);
        pwd = (EditText) findViewById(R.id.editext_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(username.getText().toString().equals("") || pwd.getText().toString().equals("")){
                    Snackbar.make(coordinatorLayout,"Non hai inserito i dati richiesti",Snackbar.LENGTH_LONG).show();
                    return;}

                else{

                    requestID(username.getText().toString(), pwd.getText().toString());



                    }





            }
        });

    }


    public void requestID(final String username, final String password){
        String tag_string_req = "req_login";

        pDialog = ProgressDialog.show(this,"Invio dati","Sto controllando i tuoi dati per il login", true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_REQUEST_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                try {

                    JSONObject jObj = new JSONObject(response);
                    Log.d("Json", jObj.toString());
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        String id = jObj.getString("id");

                        manager.setID(id);

                        // Inserting row in users table
                        SQL.addUser(id, username, password);
                        Snackbar.make(coordinatorLayout, "Login Accettato", Snackbar.LENGTH_LONG).show();
                        pDialog.dismiss();
                        // Launch main activity
                        setController(pDialog);


                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("abd", "Error: "
                        + ">>" + error.getLocalizedMessage()
                        + ">>" + error.getCause()
                        + ">>" + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();


            }
        }){

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };


        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        pDialog.dismiss();


    }

    public void setController(final ProgressDialog pDialog){

        String tag_string_req = "save_hash";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SAVE_HASH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // user successfully logged in
                        // Create login session


                        // Inserting row in users table
                        SQL.AddHash(manager.getID(),hash);
                        Snackbar.make(coordinatorLayout, "Dati di controllo salvati", Snackbar.LENGTH_LONG).show();
                        pDialog.dismiss();
                        // Launch main activity   TelephonyManager telephonyManager = (TelephonyManager) getBaseContext().getSystemService(getBaseContext().TELEPHONY_SERVICE);

                        Intent i = new Intent(Configure_Activity.this,SplashScreen.class);
                        startActivity(i);
                        finish();

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("abd", "Error: "
                        + ">>" + error.getLocalizedMessage()
                        + ">>" + error.getCause()
                        + ">>" + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();


            }
        }){

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("hash", hash);

                return params;
            }

        };


        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        pDialog.dismiss();

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        TelephonyManager telephonyManager = (TelephonyManager) getBaseContext().getSystemService(getBaseContext().TELEPHONY_SERVICE);
                        UCode = telephonyManager.getDeviceId();
                        md.update(UCode.getBytes());

                        hash=new String(md.digest());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                } else {

                    btnLogin.setEnabled(false);
                    Snackbar.make(coordinatorLayout,"L'applicazione non può funzionare senza il tuo permesso, tra qualche secondo l'app verrà chiusa",Snackbar.LENGTH_LONG).show();
                    Timer timer = new Timer(3500, 1,this,null,null);
                    timer.start();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
