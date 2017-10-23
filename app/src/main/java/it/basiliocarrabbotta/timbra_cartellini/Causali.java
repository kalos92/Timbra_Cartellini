package it.basiliocarrabbotta.timbra_cartellini;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Causali extends AppCompatActivity implements TimerObserver {

    private EditText descrizione;
    private Bundle bundle;
    private CoordinatorLayout coordinatorLayout;
    private ProgressDialog pDialog;
    private SessionManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_causali);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inserisci Causale");
        setSupportActionBar(toolbar);
        manager = new SessionManager(getBaseContext());
        bundle = getIntent().getExtras();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_causali) ;


        descrizione = (EditText) findViewById(R.id.descrizione_causa);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle!=null){

                    inviaDati(bundle.getDouble("LAT"),bundle.getDouble("LNG"),bundle.getString("USERNAME"),bundle.getString("PASSWORD"),bundle.getString("TIPO"),bundle.getString("CLOCK"),bundle.getLong("CLOCK_SENCOND"));

                }

            else{
                    Snackbar.make(coordinatorLayout, "Qualcosa è andato storto", Snackbar.LENGTH_LONG).show();
                    Intent data = new Intent(Causali.this,SplashScreen.class);
                    startActivity(data);
                    finish();

            }
            }
        });
    }



    public void inviaDati(final Double LAT, final Double LNG, final String username, final String password, final String tipo, final String clock,final Long Clock_second){

        String tag_string_req = "send_data";

        pDialog = ProgressDialog.show(this,"Invio dati","La tua timbratura sta per essere salvata", true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SEND_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        String message = jObj.getString("message");

                        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
                        pDialog.dismiss();

                        finish();
                        // Launch main activity

                    } else {
                        String errorMsg = jObj.getString("message");
                        Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG).show();
                        pDialog.dismiss();
                        Timer t = new Timer(3500,1,null,null,null);
                        t.start();

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
               // (Double LAT, Double LNG, String username, String password, String tipo, String clock, Long Clock_second
                params.put("username", username);
                params.put("password", password);
                params.put("tipo",tipo);
                params.put("time", clock);
                while(manager.getID()==null)
                params.put("userid", manager.getID());
                params.put("userid", manager.getID());
                params.put("time_millis",Long.toString(Clock_second));
                params.put("causale", descrizione.getText().toString());
                params.put("NETlat", Double.toString(LAT));
                params.put("NETlng", Double.toString(LNG));

                return params;
            }

        };


        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        pDialog.dismiss();


    }


    @Override
    public void OnTimerFinished() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
