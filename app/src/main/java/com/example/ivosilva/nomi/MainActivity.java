package com.example.ivosilva.nomi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.ivosilva.nomi.menu.MenuActivity;
import com.example.ivosilva.nomi.push_notifications.QuickstartPreferences;
import com.example.ivosilva.nomi.push_notifications.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends AppCompatActivity {
    public static final String LOGINPREFS = "LoginPrefs";
    public static final String USERID = "idKey";
    SharedPreferences shared_preferences;
    private GoogleCloudMessaging gcm;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // shared_preferences(LOGINPREFS) stores the user id
        // so if it exists, there's no need to display the login screen
        shared_preferences = getSharedPreferences(LOGINPREFS, Context.MODE_PRIVATE);

        if (shared_preferences.getInt(USERID, -1) != -1){
            Intent menu_intent = new Intent(this, MenuActivity.class);
            startActivity(menu_intent);
            finish();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);

            }
        };

    }



    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}
