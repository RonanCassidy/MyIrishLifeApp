package com.example.z034.myirishlifeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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

/**
 * Created by v584 on 27/09/2016.
 */

public class GetPolicyDetails extends AsyncTask<Void, Void, Boolean> {
    private final String targetURL;
    private final String targetMethod;
    private final String urlParameters;
    private  String string;
    private final Context context;
    private String response;
    private Boolean Timedout = false;
    String PolicyType;
    String sDate;
    String rDate;


    GetPolicyDetails(String targetURL, String method, String urlParameters, String policyid,String string, Context context){
        this.targetURL = targetURL;
        this.targetMethod = method;
        this.urlParameters = urlParameters;
        this.context = context;
        this.Timedout = false;
        this.string=string;

    }

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
            JSONObject response = null;
            try {
                response = new JSONObject(sb.toString());
                if(response.length()!=0)
                {
                    PolicyType= response.get("policyType").toString();
                    sDate= response.get("startDate").toString();
                    rDate= response.get("renewalDate").toString();
                    string=PolicyType;
                    return true;
                }

                else return false;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Timedout = true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String CreateTextViewForPolicyTable(String text)
    {
       return PolicyType;
    }
}
