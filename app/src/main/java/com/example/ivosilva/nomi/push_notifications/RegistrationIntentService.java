package com.example.ivosilva.nomi.push_notifications;

/**
 * Created by ivosilva on 03/11/15.
 */
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    private RequestQueue mQueue;

    private GoogleCloudMessaging gcm =null;

    private String device_id;
    TelephonyManager tm;
    SharedPreferences shared_preferences;


    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            //InstanceID instanceID = InstanceID.getInstance(this);
            //String token = instanceID.getToken(getResources().getString(R.string.gcm_defaultSenderId),
            //        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            gcm = GoogleCloudMessaging.getInstance(this);
            String token = gcm.register(getResources().getString(R.string.gcm_defaultSenderId));

            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);


            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        String url = "http://"+"192.168.160.56:8000"+"/api/device/gcm/";

        shared_preferences = getSharedPreferences(LoginFragment.LOGINPREFS, Context.MODE_PRIVATE);

        if (shared_preferences.getInt(LoginFragment.USERID, -1) != -1){

            mQueue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

            try {
                JSONObject jsonBody = new JSONObject("{" +
                        "\"name\": \"" + Integer.toString(shared_preferences.getInt(LoginFragment.USERID, -1)) + "\"," +
                        "\"registration_id\": \"" + token + "\"," +
                        "\"device_id\": \"" + device_id + "\"," +
                        "\"active\": true," +
                        "\"user\": \"" + Integer.toString(shared_preferences.getInt(LoginFragment.USERID, -1)) + "\"," +
                        "}");

                Log.d("JSONREG", jsonBody.toString());

                final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                Log.d("jsonObject", jsonObject.toString());

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                VolleyLog.e("error", volleyError.getMessage());
                            }
                        });

                jsonRequest.setTag("REG_PUSH");
                mQueue.add(jsonRequest);
            }catch (JSONException e){
                Log.d("JSONException", e.toString());
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
//        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
//        try {
//            Log.d("SENDERID", getResources().getString(R.string.gcm_defaultSenderId));
//            regid = gcm.register(getResources().getString(R.string.gcm_defaultSenderId));
//        }catch (IOException e){
//            Log.d("REGID", e.toString());
//        }

    }



}


