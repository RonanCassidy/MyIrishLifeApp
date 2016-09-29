package com.example.z034.myirishlifeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class Toolbox extends AppCompatActivity {
    boolean onStart=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbox);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomBar bottombar =(BottomBar) findViewById(R.id.bottomBar);
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_chat && !onStart) {
                    Intent i = new Intent(Toolbox.this, OnlineChat.class);
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
                    new android.support.v7.app.AlertDialog.Builder(Toolbox.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(Toolbox.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(Toolbox.this,"Request cancelled",Toast.LENGTH_LONG).show();
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
                    Intent i = new Intent(Toolbox.this, OnlineChat.class);
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
                    new android.support.v7.app.AlertDialog.Builder(Toolbox.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(Toolbox.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(Toolbox.this,"Request cancelled",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        onStart = false;
    }
    public void btnPenCalcClick(View v) {
        // Start NewActivity.class
        Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
        webIntent.putExtra("link", "https://www.pensionplanetinteractive.ie/prophet/");
        startActivity(webIntent);
    }


    public void btnLifeCoverClick(View v) {
        // Start NewActivity.class
        Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
        webIntent.putExtra("link", "https://www.irishlife.ie/life-insurance/term-life-insurance-quote");
        startActivity(webIntent);
    }


    public void btnHealthCoverClick(View v) {
        // Start NewActivity.class
        Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
        webIntent.putExtra("link", "http://www.irishlifehealth.ie/health-insurance-plans/?showall=1");
        startActivity(webIntent);
    }


    public void btnEnAnnClick(View v) {
        // Start NewActivity.class
        Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
        webIntent.putExtra("link", "https://www.pensionplanetinteractive.ie/ppi/public/pensionChoicePersonalDetails.action");
        startActivity(webIntent);
    }


    public void btnRiskProfilerClick(View v) {
        // Start NewActivity.class
        Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
        webIntent.putExtra("link", "https://www.pensionplanetinteractive.ie/ppi/public/loadRiskProfiler.action");
        startActivity(webIntent);
    }
    public void btnInvestClick(View v) {
        // Start NewActivity.class
        Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
        webIntent.putExtra("link", "https://www.irishlife.ie/investments");
        startActivity(webIntent);
    }


}
