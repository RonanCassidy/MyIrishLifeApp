package com.example.z034.myirishlifeapp;


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
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    // PUSH NOTE
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "PushNoteActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect to GCM Server and enable push notifications
        connectToGCMServer();
    }
    public void qrClick(View v) {
        // Start NewActivity.class
        Intent qrIntent = new Intent(getApplicationContext(), QRScannerActivity.class);
        startActivity(qrIntent);
    }
    public void btnIrlClick(View v) {
        // Start NewActivity.class

        Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
        webIntent.putExtra("link", "www.irishlife.ie");
        startActivity(webIntent);
    }
    public void contactUsClick(View v) {

        // Start NewActivity.class
        Intent contactIntent = new Intent(getApplicationContext(), ContactUsActivity.class);
        startActivity(contactIntent);
    }

    public void userDetailClick(View v) {

        Intent userDetailIntent = new Intent(getApplicationContext(), UserDetailActivity.class);
        startActivity(userDetailIntent);
    }
    public void loginButtonClick(View v) {

        Intent userDetailIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(userDetailIntent);
    }

    // PUSH NOTE -start
    public void connectToGCMServer(){
        final int duration = Toast.LENGTH_SHORT;

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(PushNotePreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.gcm_send_message), duration);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.token_error_message), duration);
                    toast.show();
                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(PushNotePreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    // PUSH NOTE -end
}