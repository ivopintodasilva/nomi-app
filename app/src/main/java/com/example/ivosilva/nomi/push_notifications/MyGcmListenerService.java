package com.example.ivosilva.nomi.push_notifications;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.Context;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.contacts.ContactDetailsActivity;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";


    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        try {
            JSONObject jsonObj = new JSONObject(message);
            JSONObject user = jsonObj.getJSONObject("user");
            JSONArray attributes = jsonObj.getJSONArray("attributes");

            String user_name = user.getString("first_name") + " " + user.getString("last_name");

            String user_arg = "{" +
                    "\"name\": \"" + user_name + "\"," +
                    "\"id\": " + user.getInt("id") + "," +
                    "\"email\": \"" + user.getString("email") + "\"" +
                    "}";

            Log.d("user_arg", user_arg);


            // construct the appropriate json to send to details view
            String contact_arg = "{";
            for(int i = 0; i < attributes.length(); i++){
                // if it's not the first key, put a comma
                if (!contact_arg.equals("{")){
                    contact_arg += ",";
                }

                contact_arg += "\"" + attributes.getJSONObject(i).getString("name") + "\": ";
                contact_arg += "\"" + attributes.getJSONObject(i).getString("value") + "\"";

            }

            contact_arg += "}";

            Log.d("contact_arg", contact_arg);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.nomi_push_smooth)
                            .setContentTitle(user_name + " " + getResources().getString(R.string.updated_info))
                            .setContentText(getResources().getString(R.string.get_in_touch))
                            .setAutoCancel(true);
            int NOTIFICATION_ID = 12345;



            Intent targetIntent = new Intent(this, ContactDetailsActivity.class);
            targetIntent.putExtra("PROFILE", user_arg);
            targetIntent.putExtra("CONTACTS", contact_arg);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(NOTIFICATION_ID, builder.build());

        }
        catch (JSONException e){
            Log.d("JSONException", e.toString());
        }

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
    }
}