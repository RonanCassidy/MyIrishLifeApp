package com.example.z034.myirishlifeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.IntentFilter;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import android.view.MotionEvent;
import android.widget.Toast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class Home extends AppCompatActivity {


    private String userId;
    private String Pin;
    private JSONArray PolicyInfo = null;
    private GridLayout PolicyGrid;
    private Button addPolicy;


    // PUSH NOTE
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "PushNoteActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        PolicyGrid = (GridLayout) findViewById(R.id.HomePolicyGrid);
        addPolicy = (Button) findViewById(R.id.AddPolicy);
        Intent intent = getIntent();
        this.userId = intent.getStringExtra(ApplicationConstants.Username);
        this.Pin = intent.getStringExtra(ApplicationConstants.Pin);
        HttpUtilities.GetPolicyData(userId, Pin, getApplicationContext(), intent, PolicyGrid);
        connectToGCMServer();
    }

    public void AddPolicyButtonClick(View v) {
        Intent addPolicyIntent = new Intent(getApplicationContext(), AddPolicyActivity.class);
        addPolicyIntent.putExtra(ApplicationConstants.Username, userId);
        addPolicyIntent.putExtra(ApplicationConstants.Pin, Pin);
        startActivity(addPolicyIntent);
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
            Intent intentService = new Intent(this, GCMRegistrationIntentService.class);
            intentService.putExtra(ApplicationConstants.Username, getIntent().getStringExtra(ApplicationConstants.Username));
            startService(intentService);
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
