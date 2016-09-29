package com.example.z034.myirishlifeapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by s970 on 26/09/2016.
 */

public class AddPolicyActivity extends AppCompatActivity {
    static final int SCAN_QR_CODE = 1;

    private static final String ADD_POLICY_URL = "/AutService.asmx/AddPolicyToUser"; // azure

    private EditText policyIdText;
    boolean onStart=true;

    private String userid, pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_policy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        policyIdText = (EditText) findViewById(R.id.policyNumberEditText);

        BottomBar bottombar =(BottomBar) findViewById(R.id.bottomBar);
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_chat && !onStart) {
                    Intent i = new Intent(AddPolicyActivity.this, OnlineChat.class);
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
                    new android.support.v7.app.AlertDialog.Builder(AddPolicyActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AddPolicyActivity.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AddPolicyActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
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
                    Intent i = new Intent(AddPolicyActivity.this, OnlineChat.class);
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
                    new android.support.v7.app.AlertDialog.Builder(AddPolicyActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AddPolicyActivity.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AddPolicyActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        onStart = false;


        Bundle extras = getIntent().getExtras();
        if (extras != null){
            userid = extras.getString(ApplicationConstants.Username);
            pincode = extras.getString(ApplicationConstants.Pin);
        }
    }

    public void btnScanPolicyClick(View v) {
        Intent scannerIntent = new Intent(getApplicationContext(), QRScannerActivity.class);
        startActivityForResult(scannerIntent, SCAN_QR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_QR_CODE) {

            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    final String policyId = data.getStringExtra("qrcode_details");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Confirm Addition of Policy");
                    builder.setMessage("Policy " + policyId + " will be added to your account");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String parameters = "userID=" + userid + "&pin=" + pincode + "&policyID=" + policyId;
                            new AddPolicy(parameters).execute(parameters);
                            displayPolicyDetails(policyId);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        }

    }

    public void btnAddPolicyClick(View v) {
        if (policyIdText.getText().length()>1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm Addition of Policy");
            builder.setMessage("Policy " + policyIdText.getText() + " will be added to your account");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String parameters = "userID=" + userid +"&pin=" + pincode + "&policyID=" + policyIdText.getText();
                    new AddPolicy(parameters).execute(parameters);
                    displayPolicyDetails(policyIdText.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void displayPolicyDetails(String policyNumber){
        Intent addPolicyIntent = new Intent(getApplicationContext(), PolicyDetailsActivity.class);
        addPolicyIntent.putExtra("PolicyNumber",policyNumber);
        addPolicyIntent.putExtra(ApplicationConstants.Username, userid);
        addPolicyIntent.putExtra(ApplicationConstants.Pin, pincode);
        startActivity(addPolicyIntent);
    }

    private class AddPolicy extends AsyncTask<String, String, String> {
        private final String parameters;

        AddPolicy(String parameters) {
            this.parameters = parameters;
        }
        ProgressDialog pdLoading = new ProgressDialog(AddPolicyActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tAdding Policy...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String server = "";
            try {
                server = Util.getProperty("server",getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            };
            try {
                url = new URL(server + ADD_POLICY_URL+"?"+parameters);
                System.out.println(url);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {
                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //this method will be running on UI thread
            pdLoading.dismiss();
            if (result != "unsuccessful"){
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    if(jsonResponse.get("updateSuccessful").toString() == "true") {
                        Toast.makeText(AddPolicyActivity.this, "Policy Added Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddPolicyActivity.this, "Unable to add policy", Toast.LENGTH_LONG).show();
                        System.out.println(result);
                    }

                } catch (JSONException e) {
                    Toast.makeText(AddPolicyActivity.this, "Unable to add policy ", Toast.LENGTH_LONG).show();
                    System.out.println(e.toString());
                }
            } else {
                Toast.makeText(AddPolicyActivity.this, "Unable to add policy ", Toast.LENGTH_LONG).show();
                System.out.println(result);
            }


        }

    }
}
