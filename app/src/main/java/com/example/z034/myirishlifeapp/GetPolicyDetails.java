package com.example.z034.myirishlifeapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
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
    private final String policyid;
    private final Context context;
    String Type;
    GetPolicyDetails(String targetURL, String method, String urlParameters, String policyid, Context context){
        this.targetURL = targetURL;
        this.targetMethod = method;
        this.urlParameters = urlParameters;
        this.policyid=policyid;
        this.context = context;
    }

    protected Boolean doInBackground(Void... params) {
        URL myurl = null;
        try {
            myurl = new URL(targetURL + targetMethod + "?" + urlParameters);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            URLConnection myconn = myurl.openConnection();
            InputStream blah = myconn.getInputStream();
            InputStream in = new BufferedInputStream(blah);
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
                if(response.length()!=0){
                    Type=response.get("policyType").toString();
                    Type=response.get("startDate").toString();
                    Type=response.get("renewalDate").toString();
                    Type=response.get("policyID").toString();
                    return true;}
                else return false;
            } catch (JSONException e) {
                e.printStackTrace();
                //String err = "Cannot create JSONObject with response from server. Response '" + sb.toString() +"'";
                //throw new JSONException(err);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    protected void onPostExecute(final Boolean success) {
        if (success) {
            Intent home = new Intent(context, Home.class);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(home);
            String s1 = "Succesdfhkks!!!";
            //redirect
        } else {

        }
    }
}
