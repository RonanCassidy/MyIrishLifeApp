package com.example.z034.myirishlifeapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.zxing.Result;

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
 * Created by s970 on 27/09/2016.
 */

public class InitiateWebSessionActivity extends AppCompatActivity {

    static final int SCAN_QR_CODE = 2;
    private static final String WEB_SESSION_URL = "http://52.174.106.218/AutService.asmx/AddSession";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_web_session);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void btnScanWebSessionClick(View v) {
        Intent scannerIntent = new Intent(getApplicationContext(), QRScannerActivity.class);
        startActivityForResult(scannerIntent, SCAN_QR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_QR_CODE) {

            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    final String sessionId = data.getStringExtra("qrcode_details");
                    String parms = "sessionID="+sessionId + "&userID=Yvonne.Mongo856&pin=1234";
                    new StartWebSession(parms).execute();
                }


            }
        }

    }

    private class StartWebSession extends AsyncTask<String, String, String> {
        private final String parameters;

        StartWebSession(String parameters) {
            this.parameters = parameters;
        }
        ProgressDialog pdLoading = new ProgressDialog(InitiateWebSessionActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tSending details...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(WEB_SESSION_URL+"?"+parameters);
                System.out.println("URL: "+url);
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
                System.out.println("response code " + response_code);
                System.out.println("response "+ conn.getResponseMessage());
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
                    // Pass data to onPostExecute method
                    System.out.println("do in background" + result.toString());
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
                    System.out.println("on post execute" + result);
                    JSONObject jsonResponse = new JSONObject(result);
                    if(jsonResponse.get("sessionAuthenticated").toString() == "true") {
                        Toast.makeText(InitiateWebSessionActivity.this, "Session started successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(InitiateWebSessionActivity.this, "Unable to start session", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(InitiateWebSessionActivity.this, "Unable to start session ", Toast.LENGTH_LONG).show();
                    System.out.println(e.toString());
                }
            } else {
                Toast.makeText(InitiateWebSessionActivity.this, "Unable to start session ", Toast.LENGTH_LONG).show();
                System.out.println(result);
            }


        }

    }

}
