package com.example.z034.myirishlifeapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}