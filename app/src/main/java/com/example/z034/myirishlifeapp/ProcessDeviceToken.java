package com.example.z034.myirishlifeapp;

/**
 * Created by y424 on 28/09/2016.
 */

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

public class ProcessDeviceToken extends AsyncTask<Void, Void, Boolean> {

    private final String targetURL;
    private final String targetMethod;
    private final String username;
    private final String token;

    ProcessDeviceToken(String targetURL, String method, String username, String token) {
        this.targetURL = targetURL;
        this.targetMethod = method;
        this.username = username;
        this.token = token;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        URL myurl = null;
        //URL addDeviceURL = null;

        try {
            //userID=Yvonne.Mongo856&deviceID=
            myurl = new URL(targetURL + targetMethod + "?" + "userID=" + username + "&deviceID=" + token);
            //addDeviceURL = new URL("http://10.233.204.232:9090/autService.asmx/StoreUserDevice?userID=Yvonne.Mongo856&deviceID="+token);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpUtilities.GetServerResponse(myurl);
            //HttpUtilities.GetServerResponse(addDeviceURL);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
