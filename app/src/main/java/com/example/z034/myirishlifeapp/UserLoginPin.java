package com.example.z034.myirishlifeapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

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

public class UserLoginPin extends AsyncTask<Void, Void, Boolean> {

    private final String targetURL;
    private final String targetMethod;
    private final String urlParameters;
    private final EditText pinField;
    private final Context context;
    private final String UserID;
    private final String Pin;
    private Boolean Timedout = false;

    UserLoginPin(String targetURL, String method, String username, String pin, EditText pinField, Context context) {
        this.targetURL = targetURL;
        this.targetMethod = method;
        this.UserID = username;
        this.Pin = pin;
        String query = "userID=" + username + "&" + "pin=" + pin;
        this.urlParameters = query;
        this.pinField = pinField;
        this.context = context;
        this.Timedout = false;
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
                if(response.get("authenticated").toString() == "true") return true;
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

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            Intent home = new Intent(context, Home.class);
            home.putExtra(ApplicationConstants.Username, this.UserID);
            home.putExtra(ApplicationConstants.Pin, this.Pin);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(home);
        } else if(Timedout){
            pinField.setError("Timed out connecting to server");
            Timedout = false;
        }
        else {
            pinField.setError("Pin is invalid");
        }
    }
}