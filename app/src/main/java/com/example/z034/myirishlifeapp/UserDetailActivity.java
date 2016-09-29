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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by s970 on 21/09/2016.
 */

public class UserDetailActivity extends AppCompatActivity {

    boolean onStart=true;


    private static final String TAG_FORENAME = "forename";
    private static final String TAG_SURNAME = "surname";
    private static final String TAG_DOB = "dob";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_MOBILE = "mobile";
    private static final String TAG_ADDRESS = "address";

    private static final String FETCH_URL= "/AutService.asmx/GetUserDetails"; //azure
    private static final String SAVE_URL = "/AutService.asmx/SaveUserDetails"; // azure
    private static final String POSTCODE_LOOKUP_URL = "https://api.autoaddress.ie/2.0/PostcodeLookup";
    private static final String AUTOADDRESS_KEY = "10F069B3-5A3F-4bfc-B2CB-F062DA84C102";

    private JSONObject jsonobject;
    private EditText name, dob, email, phone, address, eircode;
    private TextView eircodeView;
    private Button update, save, cancel, useeircode;
    private boolean updated, canListenInput;
    private String userid, pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);
        canListenInput = false;

        BottomBar bottombar =(BottomBar) findViewById(R.id.bottomBar);
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_chat && !onStart) {
                    Intent i = new Intent(UserDetailActivity.this, OnlineChat.class);
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
                    new android.support.v7.app.AlertDialog.Builder(UserDetailActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserDetailActivity.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserDetailActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
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
                    Intent i = new Intent(UserDetailActivity.this, OnlineChat.class);
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
                    new android.support.v7.app.AlertDialog.Builder(UserDetailActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserDetailActivity.this,"Your request has been logged. You will receive a call within 24 hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserDetailActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        onStart = false;

        name = (EditText) findViewById(R.id.name);
        dob = (EditText) findViewById(R.id.dob);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);
        eircode = (EditText) findViewById(R.id.eircode);

        eircodeView = (TextView) findViewById(R.id.eircodeView);

        update = (Button) findViewById(R.id.update);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        useeircode = (Button) findViewById(R.id.useeircode);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            userid = extras.getString(ApplicationConstants.Username);
            pincode = extras.getString(ApplicationConstants.Pin);
        }


        TextWatcher tw = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (canListenInput) {
                    updated = true;
                }
            }
        };

        new FetchUser().execute();

        email.addTextChangedListener(tw);
        phone.addTextChangedListener(tw);
        address.addTextChangedListener(tw);

        // disable save/cancel
        disableEditing();

    }

    public void updateClick(View v)
    {

        enableEditing();
    }

    public void saveClick(View v)
    {
        if (updated) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm Update");
            builder.setMessage("These changes will be made to all your Irish Life policies");
            builder.setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // save to db ...
                    disableEditing();
                    dialog.dismiss();
                    // change to use real userId and password
                    String urlParams = null;
                    try {
                        urlParams = "userID="+userid+"&pin=" + pincode +
                                "&email="+ URLEncoder.encode(email.getText().toString(), "UTF-8") +
                                "&mobile=" + URLEncoder.encode(phone.getText().toString(), "UTF-8") +
                                "&address=" + URLEncoder.encode(address.getText().toString(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    new SaveUser(urlParams).execute();
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

    public void cancelClick(View v)
    {
        finish();
    }

    public void useEircodeClick(View v)
    {
        if(eircode.getText().length() > 6){
            new PostcodeLookup(eircode.getText().toString()).execute();
        } else {
            Toast.makeText(UserDetailActivity.this, "Please enter eircode", Toast.LENGTH_LONG).show();
        }

    }

    private void enableEditing(){
        update.setEnabled(false);
        update.setVisibility(View.GONE);
        save.setEnabled(true);
        save.setVisibility(View.VISIBLE);
        cancel.setEnabled(true);
        cancel.setVisibility(View.VISIBLE);
        email.setEnabled(true);
        phone.setEnabled(true);
        address.setEnabled(true);
        eircode.setVisibility(View.VISIBLE);
        eircodeView.setVisibility(View.VISIBLE);
        useeircode.setEnabled(true);
        useeircode.setVisibility(View.VISIBLE);
        canListenInput = true;
    }

    private void disableEditing(){
        update.setEnabled(true);
        update.setVisibility(View.VISIBLE);
        save.setEnabled(false);
        save.setVisibility(View.GONE);
        cancel.setEnabled(false);
        cancel.setVisibility(View.GONE);
        eircodeView.setVisibility(View.GONE);
        useeircode.setEnabled(false);
        useeircode.setVisibility(View.GONE);
        eircode.setVisibility(View.GONE);
        email.setEnabled(false);
        phone.setEnabled(false);
        address.setEnabled(false);
    }
    private class FetchUser extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(UserDetailActivity.this);
        HttpURLConnection conn;
        URL url = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(true);
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
                url = new URL(server + FETCH_URL+"?userID="+userid+"&pin=" + pincode);
                System.out.println("***" + url);

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
                    // Pass data to onPostExecute method
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
            try {
                JSONObject jsonobject = new JSONObject(result);
                try {
                    name.setText(jsonobject.getString(TAG_FORENAME) + " " + jsonobject.getString(TAG_SURNAME));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    dob.setText(jsonobject.getString(TAG_DOB));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    email.setText(jsonobject.getString(TAG_EMAIL));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    phone.setText(jsonobject.getString(TAG_MOBILE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    address.setText(jsonobject.getString(TAG_ADDRESS));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                Toast.makeText(UserDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class SaveUser extends AsyncTask<String, String, String> {
        private final String parameters;

        SaveUser(String parameters) {
            this.parameters = parameters;
        }
        ProgressDialog pdLoading = new ProgressDialog(UserDetailActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tUpdating...");
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
                url = new URL(server + SAVE_URL+"?"+parameters);
                System.out.println("***" + url);
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
                System.out.println("response_code "+response_code);
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
            try {
                JSONObject jsonResponse = new JSONObject(result);
                if(jsonResponse.get("updateSuccessful").toString() == "true") {
                    Toast.makeText(UserDetailActivity.this, "Details Saved Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UserDetailActivity.this, "Unable to update details", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                Toast.makeText(UserDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }

    private class PostcodeLookup extends AsyncTask<String, String, String> {
        private final String postcode;
        PostcodeLookup(String postcode) {
            this.postcode = postcode;
        }

        ProgressDialog pdLoading = new ProgressDialog(UserDetailActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tChecking...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // need to change this to use real userId and pin!!
                url = new URL(POSTCODE_LOOKUP_URL+"?key="+AUTOADDRESS_KEY+"&postcode="+postcode+"&vanityMode=true");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                // only needed for emulating on pc, remove before demo
                //Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("ilserverproxy.europa.internal",8080));
                //conn = (HttpURLConnection) url.openConnection(proxy);
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
                    // Pass data to onPostExecute method
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
            try {

                JSONObject jsonobject = new JSONObject(result);
                JSONArray addressArray = jsonobject.getJSONArray("vanityAddress");

                String postalAddress = "";
                for (int i = 0; i < addressArray.length(); i++) {
                    postalAddress = postalAddress + addressArray.getString(i) + "\n";
                }

                address.setText(postalAddress.substring(0, postalAddress.length()-1));
            } catch (JSONException e) {
                Toast.makeText(UserDetailActivity.this, "No address found", Toast.LENGTH_LONG).show();
                System.out.println(e.toString());
            }
        }
    }
}

