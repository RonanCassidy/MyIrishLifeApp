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

public class UserLoginPin extends AsyncTask<Void, Void, Boolean> {

    private final String targetURL;
    private final String targetMethod;
    private final String urlParameters;
    private final EditText pinField;
    private final Context context;

    UserLoginPin(String targetURL, String method, String urlParameters, EditText pinField, Context context) {
        this.targetURL = targetURL;
        this.targetMethod = method;
        this.urlParameters = urlParameters;
        this.pinField = pinField;
        this.context = context;
    }

    @Override
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
                if(response.get("authenticated").toString() == "true") return true;
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

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            Intent home = new Intent(context, Home.class);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(home);
            String s1 = "Succesdfhkks!!!";
            //redirect
        } else {
            pinField.setError("Pin is invalid");
        }
    }
}