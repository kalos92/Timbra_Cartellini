package it.basiliocarrabbotta.timbra_cartellini;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Kalos on 26/09/2017.
 */

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE=0;
    private static final String PREF_NAME = "UserChar";
    private static final String KEY_IS_SET = "Setted";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setID(String ID) {

        editor.putString(KEY_IS_SET, ID);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public String getID(){
        return pref.getString(KEY_IS_SET, null);
    }

}
