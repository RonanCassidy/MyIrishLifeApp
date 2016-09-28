package com.example.z034.myirishlifeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetUserPolicyInformation extends AsyncTask<Void, Void, Boolean> {
    private final String targetURL;
    private final String targetMethod;
    private final String urlParameters;
    private final Context context;
    private final Intent intent;
    private final String UserID;
    private final String Pin;
    private GridLayout PolicyGrid;
    private String response;

    GetUserPolicyInformation(String targetURL, String method, String userId, String pin, Context context, Intent intent, GridLayout PolicyGrid)
    {
        this.targetURL = targetURL;
        this.targetMethod = method;
        this.UserID = userId;
        this.Pin = pin;
        this.urlParameters = "userID=" + userId + "&" + "pin=" + pin;
        this.context = context;
        this.intent = intent;
        this.PolicyGrid = PolicyGrid;
        this.response = "";
    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        URL myurl = null;
        try {
            myurl = new URL(targetURL + targetMethod + "?" + urlParameters);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            URLConnection myconn = myurl.openConnection();
            InputStream in = new BufferedInputStream(myconn.getInputStream());
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            this.response = sb.toString();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success)
    {
        if(success)
        {
            this.intent.putExtra(ApplicationConstants.PolicyInfoCollection, response);

            JSONArray PolicyInfo = null;
            String PolicyInfoResponse = this.intent.getStringExtra(ApplicationConstants.PolicyInfoCollection);
            try{
                PolicyInfo = new JSONArray(PolicyInfoResponse);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            if(PolicyInfo != null)
            {
                for(int i=0;i < PolicyInfo.length();i++)
                {
                    try {
                        JSONObject row = new JSONObject(PolicyInfo.get(i).toString());
                        String policyType = row.get(ApplicationConstants.PolicyType).toString();
                        String policyNumber = row.get(ApplicationConstants.PolicyId).toString();

                        PolicyGrid.addView(this.CreateTextViewForPolicyTable(policyType, false));
                        PolicyGrid.addView(this.CreateTextViewForPolicyTable(policyNumber, true));

                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            // Show error collecting data from db
            Toast.makeText(this.context, "Failed to collect data from database ", Toast.LENGTH_LONG);
        }
    }

    private TextView CreateTextViewForPolicyTable(String text, Boolean isPolicyNumber)
    {
        TextView cell = new TextView(this.context);
        cell.setText(text);
        cell.setTextSize(18);
        cell.setWidth(18);
        cell.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.MATCH_PARENT);
        cell.setLayoutParams(params);
        if(isPolicyNumber) {
            PolicyGridOnClickListner listener = new PolicyGridOnClickListner(this.UserID, this.Pin, text, this.context, this.intent);
            cell.setOnClickListener(listener);
        }
        return cell;
    }

    /*private Button CreateButtonForPolicyNumber(String text)
    {
        Button cell = new Button(this.context);
        cell.setText(text);
        cell.setTextSize(18);
        cell.setWidth(18);
        cell.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.MATCH_PARENT);
        cell.setBackgroundColor(Color.WHITE);
        cell.(R.style.BorderLessButton);
        cell.setLayoutParams(params);
        return cell;
    }*/
}

