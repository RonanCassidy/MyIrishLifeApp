package com.example.z034.myirishlifeapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

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
    private static final String ADD_POLICY_URL = "http://52.174.106.218/AutService.asmx/AddPolicyToUser"; // azure
    //private static final String ADD_POLICY_URL = "http://10.233.204.232:9090/AutService.asmx/AddPolicyToUser"; // internal
    private EditText policyIdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_policy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        policyIdText = (EditText) findViewById(R.id.policyNumberEditText);
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
                            String parameters = "userID=Yvonne.Mongo856&pin=1234" + "&policyID=" + policyId;
                            new AddPolicy(parameters).execute(parameters);
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
                    String parameters = "userID=Yvonne.Mongo856&pin=1234" + "&policyID=" + policyIdText.getText();
                    new AddPolicy(parameters).execute(parameters);
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
            try {
                url = new URL(ADD_POLICY_URL+"?"+parameters);
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
