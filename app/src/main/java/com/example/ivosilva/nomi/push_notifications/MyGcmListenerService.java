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
import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";


    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.gito)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.gito))
                        .setContentTitle("My Notification Title")
                        .setContentText("Something interesting happened");
        int NOTIFICATION_ID = 12345;

        Intent targetIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
    }
}