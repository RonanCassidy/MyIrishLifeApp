package com.example.z034.myirishlifeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    boolean onStart=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onStart = false;
    }
    public void qrClick(View v) {
        // Start NewActivity.class
        Intent qrIntent = new Intent(getApplicationContext(), QRScannerActivity.class);
        startActivity(qrIntent);
    }
    public void btnIrlClick(View v) {
        // Start NewActivity.class
        Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
        webIntent.putExtra("link", "http://www.irishlife.ie");
        startActivity(webIntent);
    }
    public void btnMyQrClick(View v) {
        // Start NewActivity.class
        Intent myQrIntent = new Intent(getApplicationContext(), MyQrCode.class);
        startActivity(myQrIntent);
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
}