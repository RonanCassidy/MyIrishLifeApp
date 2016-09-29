package com.example.z034.myirishlifeapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by y424 on 23/09/2016.
 */

public class MyGCMListenerService extends GcmListenerService {
    private static final String TAG = "MyGCMListenerService";
    final static String GROUP_PUSH_NOTIFICATIONS = "group_push_notifications";
    private static int id = 0;

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String notificationIDStr = data.getString("msgID");
        int notificationID = Integer.parseInt(notificationIDStr);
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        //sendNotification(notificationID, message);
        sendNotification(id++, message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(int notificationID, String message) {
        //Context context = this;
        Intent intent;
        if (message.contains("http") || message.contains("www"))
        {
            intent = new Intent(this, WebActivity.class);
            intent.putExtra("link", message);
        }
        else
            intent = new Intent(this, Home.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap largeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ilsquarelogo_notifications);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("My Irish Life App")
                .setContentText(message)
                .setSmallIcon(R.drawable.ilsquarelogo_notifications)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setColor(getResources().getColor(R.color.iconBackground))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setGroup(GROUP_PUSH_NOTIFICATIONS);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID, notificationBuilder.build());
    }
}
