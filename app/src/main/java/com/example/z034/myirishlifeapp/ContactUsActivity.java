package com.example.z034.myirishlifeapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by j518 on 22/09/2016.
 */

public class ContactUsActivity extends AppCompatActivity {
    boolean onStart=true;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView number =(TextView) findViewById(R.id.number);
        final TextView email =(TextView) findViewById(R.id.email);
        final TextView address1 = (TextView) findViewById(R.id.address1);
        final TextView address2 = (TextView) findViewById(R.id.address2);
        final TextView address3 = (TextView) findViewById(R.id.address3);
        Button requestcallback = (Button) findViewById(R.id.btnRequestCall);
        BottomBar bottombar =(BottomBar) findViewById(R.id.bottomBar);
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_chat && !onStart) {
                    Intent i = new Intent(ContactUsActivity.this, OnlineChat.class);
                    startActivity(i);
                }
                if (tabId == R.id.tab_call && !onStart) {
                    String phno="tel:00353872411677";
                    Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                    startActivity(i);
                }
                if (tabId == R.id.tab_email && !onStart) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"gunninmm@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }
                if (tabId == R.id.tab_request && !onStart) {
                    new AlertDialog.Builder(ContactUsActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ContactUsActivity.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ContactUsActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });
        bottombar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_chat && !onStart) {
                    Intent i = new Intent(ContactUsActivity.this, OnlineChat.class);
                    startActivity(i);
                }
                if (tabId == R.id.tab_call) {
                    String phno="tel:00353872411677";
                    Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                    startActivity(i);
                }
                if (tabId == R.id.tab_email) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"gunninmm@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }
                if (tabId == R.id.tab_request && !onStart) {
                    new AlertDialog.Builder(ContactUsActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ContactUsActivity.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ContactUsActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        onStart = false;

         number.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String phone_no= number.getText().toString().replaceAll("-", "");
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phone_no));
                startActivity(callIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"gunninmm@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
            }
        });
        requestcallback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new AlertDialog.Builder(ContactUsActivity.this)
                        .setTitle("Request Call Back")
                        .setMessage("Do you wish to request a call back on this device?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ContactUsActivity.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                // we can send details of where they are in the app currently at this point
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ContactUsActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        address1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mapcall = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapcall);
            }
        });
        address2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mapcall = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapcall);
            }
        });
        address3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mapcall = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapcall);
            }
        });


}




    }

