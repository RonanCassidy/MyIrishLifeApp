package com.example.z034.myirishlifeapp;

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

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;




/**
 * Created by v584 on 27/09/2016.
 */

public class PolicyDetailsActivity extends AppCompatActivity {

    boolean onStart = true;
    int sPlanType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policy_details);
        Bundle extras = getIntent().getExtras();
        int PlanType = extras.getInt("PlanType");

        TextView vartext1 = (TextView) findViewById(R.id.Vartext1);
        TextView vartext2 = (TextView) findViewById(R.id.Vartext2);
        TextView vartext3 = (TextView) findViewById(R.id.Vartext3);

        TextView PolicyHolder = (TextView) findViewById(R.id.PolicyHolder);
        TextView DOB = (TextView) findViewById(R.id.DOB);
        TextView PlanName = (TextView) findViewById(R.id.PlanName);
        TextView PlanNumber = (TextView) findViewById(R.id.PlanNumber);
        TextView PType = (TextView) findViewById(PlanType);

        Button furtherdetails = (Button) findViewById(R.id.btnfurtherdetails);
        BottomBar bottombar = (BottomBar) findViewById(R.id.bottomBar);
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_chat && !onStart) {
                    Intent i = new Intent(PolicyDetailsActivity.this, OnlineChat.class);
                    startActivity(i);
                }
                if (tabId == R.id.tab_call && !onStart) {
                    String phno = "tel:00353872411677";
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                    startActivity(i);
                }
                if (tabId == R.id.tab_email && !onStart) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gunninmm@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }
                if (tabId == R.id.tab_request && !onStart) {
                    new AlertDialog.Builder(PolicyDetailsActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(PolicyDetailsActivity.this, "Your request has been logged. You will receive a call within x hours", Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(PolicyDetailsActivity.this, "Request cancelled", Toast.LENGTH_LONG).show();
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
                    Intent i = new Intent(PolicyDetailsActivity.this, OnlineChat.class);
                    startActivity(i);
                }
                if (tabId == R.id.tab_call) {
                    String phno = "tel:00353872411677";
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                    startActivity(i);
                }
                if (tabId == R.id.tab_email) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gunninmm@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }
                if (tabId == R.id.tab_request && !onStart) {
                    new AlertDialog.Builder(PolicyDetailsActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(PolicyDetailsActivity.this, "Your request has been logged. You will receive a call within x hours", Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(PolicyDetailsActivity.this, "Request cancelled", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });


        onStart = false;

       //HttpUtilities.RetreivePolicyDetail("Yvonne.Mongo856","1234","ILH574154",getApplicationContext());


        if (PlanType == 1) {
            vartext1.setText("Date of Retirement:");
            vartext2.setText("Current Fund Value:");
            vartext3.setText("Monthly Premium:");

        }
        if (PlanType == 2) {
            vartext1.setText("Maturity Date:");
            vartext2.setText("Benefit Amount:");
            vartext3.setText("Monthly Premium:");

        }
        if (PlanType == 3) {
            vartext1.setText("Cover Start Date:");
            vartext2.setText("Renewal Date:");
            vartext3.setText("Monthly Premium:");

        }
        if (PlanType == 4) {
            vartext1.setText("Maturity Date:");
            vartext2.setText("Current Fund Value:");
            vartext3.setText("Amount Invested:");

        }
        sPlanType=PlanType;
    }
    public void FurtherDetailsClick(View v) {

        Intent furtherdetails = new Intent(getApplicationContext(), FurtherDetailsActivity.class);
        furtherdetails.putExtra("PlanType",sPlanType);
        startActivity(furtherdetails);
    }
}
