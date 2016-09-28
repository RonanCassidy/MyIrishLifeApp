package com.example.z034.myirishlifeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;


/**
 * Created by v584 on 27/09/2016.
 */

public class PolicyDetailsActivity extends AppCompatActivity {
    private static final String FETCH_URL = "http://52.174.106.218/AutService.asmx/"; //azure
    private static final String User_URL="GetUserDetails";
    private static final String Policy_URL="GetUserPolicyDetails";
    boolean onStart = true;
    String PolicyNumber;
    String mPlanType;
    private static final String TAG_FORENAME = "forename";
    private static final String TAG_SURNAME = "surname";
    private static final String TAG_DOB = "dob";
    private static final String TAG_PolicyType = "policyType";
    private static final String TAG_PolicyID = "policyID";

    //Health
    private static final String TAG_StartDate = "startDate";
    private static final String TAG_RenewalDate = "renewalDate";

    //Savings
    private static final String TAG_retireDate = "retireDate";
    private static final String TAG_schemeNo = "schemeNo";
    private static final String TAG_schemeName = "schemeName";

    //Savings
    /* These will change
    private static final String TAG_retireDate = "retireDate";
    private static final String TAG_schemeNo = "schemeNo";
    private static final String TAG_schemeName = "schemeName";
    */
    //Investments
    /*These will change
    private static final String TAG_retireDate = "retireDate";
    private static final String TAG_schemeNo = "schemeNo";
    private static final String TAG_schemeName = "schemeName";
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policy_details);
        Bundle extras = getIntent().getExtras();
        String PolicyNo = extras.getString("PolicyNumber");

        TextView vartext1 = (TextView) findViewById(R.id.Vartext1);
        TextView vartext2 = (TextView) findViewById(R.id.Vartext2);
        TextView vartext3 = (TextView) findViewById(R.id.Vartext3);
        TextView vardata3 = (TextView) findViewById(R.id.Var3);


        TextView PType = (TextView) findViewById(R.id.PlanType);

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


        //HttpUtilities.RetreivePolicyDetail("Yvonne.Mongo856","1234","ILH574154",pType,getApplicationContext());
        new FetchUser().execute();
        FetchPolicyDetails PolicyDetails = new FetchPolicyDetails(PolicyNo);
        PolicyDetails.execute(PolicyNo);



    }

    public void FurtherDetailsClick(View v) {

        Intent furtherdetails = new Intent(getApplicationContext(), FurtherDetailsActivity.class);
        furtherdetails.putExtra("PlanType", mPlanType);
        startActivity(furtherdetails);
    }

    private class FetchUser extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(PolicyDetailsActivity.this);
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
            try {
                // need to change this to use real userId and pin!!
                url = new URL(FETCH_URL +User_URL+ "?userID=Yvonne.Mongo856&pin=1234");

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
            TextView PolicyHolder = (TextView) findViewById(R.id.PolicyHolder);
            TextView DOB = (TextView) findViewById(R.id.DOB);

            pdLoading.dismiss();
            try {
                JSONObject jsonobject = new JSONObject(result);
                try {
                    PolicyHolder.setText(jsonobject.getString(TAG_FORENAME) + " " + jsonobject.getString(TAG_SURNAME));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    DOB.setText(jsonobject.getString(TAG_DOB));
                    } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                Toast.makeText(PolicyDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


private class FetchPolicyDetails extends AsyncTask<String, String, String> {
    ProgressDialog pdLoading = new ProgressDialog(PolicyDetailsActivity.this);
    HttpURLConnection conn;
    URL url = null;
    private final String PolicyNumber;

    FetchPolicyDetails(String PolicyNumber)
    {
        this.PolicyNumber = PolicyNumber;

    }

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
        try {
            // need to change this to use real userId and pin!!
            url = new URL(FETCH_URL +Policy_URL+ "?userID=Yvonne.Mongo856&pin=1234&policyID="+PolicyNumber);

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
        TextView PolicyType = (TextView) findViewById(R.id.PlanType);
        TextView PolicyName = (TextView) findViewById(R.id.PlanName);
        TextView sDate = (TextView) findViewById(R.id.Var1);
        TextView rDate = (TextView) findViewById(R.id.Var2);
        TextView PolicyNum = (TextView) findViewById(R.id.PlanNumber);
        TextView vartext1 = (TextView) findViewById(R.id.Vartext1);
        TextView vartext2 = (TextView) findViewById(R.id.Vartext2);
        TextView vartext3 = (TextView) findViewById(R.id.Vartext3);
        TextView vardata1 = (TextView) findViewById(R.id.Var1);
        TextView vardata2 = (TextView) findViewById(R.id.Var2);
        TextView vardata3 = (TextView) findViewById(R.id.Var3);
        pdLoading.dismiss();
        try {
            JSONObject jsonobject = new JSONObject(result);
            try {
                PolicyType.setText(jsonobject.getString(TAG_PolicyType));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                PolicyNum.setText(jsonobject.getString(TAG_PolicyID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                PolicyName.setText(jsonobject.getString(TAG_PolicyType)+"1234");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try{
                String pType=jsonobject.getString(TAG_PolicyType);
                if (pType.equals("Pension")) {
                vartext1.setText("Date of Retirement:");
                vartext2.setText("Current Fund Value:");
                vartext3.setText("Monthly Premium:");

                }
                if (pType.equals("Savings")) {
                vartext1.setText("Maturity Date:");
                vartext2.setText("Benefit Amount:");
                vartext3.setText("Monthly Premium:");

                }
                if (pType.equals("Health") ) {
                vartext1.setText("Cover Start Date:");
                vartext2.setText("Renewal Date:");
                vartext3.setText("Monthly Premium:");
                vardata3.setText("€100.00");
                    try {
                        sDate.setText(jsonobject.getString(TAG_StartDate));
                    } catch (JSONException e) {
                        e.printStackTrace();}
                    try {
                        rDate.setText(jsonobject.getString(TAG_RenewalDate));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (pType.equals("Investment")) {
                vartext1.setText("Maturity Date:");
                vartext2.setText("Current Fund Value:");
                vartext3.setText("Amount Invested:");

    }
                mPlanType=pType;//Setting this to be used behind the button click
            } catch (JSONException e) {
    e.printStackTrace();
}

        } catch (JSONException e) {
            Toast.makeText(PolicyDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }


        }
    }
}
