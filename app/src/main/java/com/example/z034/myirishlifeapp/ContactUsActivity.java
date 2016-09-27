package com.example.z034.myirishlifeapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by j518 on 22/09/2016.
 */

public class ContactUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
