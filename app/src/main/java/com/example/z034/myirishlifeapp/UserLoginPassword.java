package com.example.z034.myirishlifeapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.net.MalformedURLException;
import java.net.URL;

public class UserLoginPassword extends AsyncTask<Void, Void, Boolean> {

    private final String TargetURL;
    private final String TargetMethod;
    private final String UrlParameters;
    private final Context Context;
    private final Intent Intent;
    private final String UserID;
    private final String Password;
    private String Response;

    // PUSH NOTE
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "PushNoteActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    boolean onStart=true;

    UserLoginPassword(String targetURL, String method, String userId, String password, Context context, Intent intent)
    {
        this.TargetURL = targetURL;
        this.TargetMethod = method;
        this.UserID = userId;
        this.Password = password;
        this.UrlParameters = "userID=" + userId + "&" + "password=" + password;
        this.Context = context;
        this.Intent = intent;
        this.Response = "";

    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        URL myurl = null;
        try {
            myurl = new URL(TargetURL + TargetMethod + "?" + UrlParameters);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String response = HttpUtilities.GetServerResponse(myurl);

        if(response != null)
        {
            this.Response = response;
            return true;
        }
        else return false;
    }

    @Override
    protected void onPostExecute(final Boolean success)
    {
        if(success)
        {
            if(HttpUtilities.GetAuthenticatedYesNo(this.Response))
            {
                // Prepare new activity
                Intent home = new Intent(this.Context, DrawerActivity.class);
                home.putExtra(ApplicationConstants.Username, this.UserID);
                home.putExtra(ApplicationConstants.Pin, "1234");
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Save account to local device for future login
                if(!AccountUtilities.AddAccount(this.Context, this.UserID, this.Password)) Toast.makeText(this.Context, "Unable to save account to your local device", Toast.LENGTH_SHORT);
                this.Context.startActivity(home);
            }
            else
            {
                Toast.makeText(this.Context, "Incorrect UserId or Password", Toast.LENGTH_SHORT);
            }
        }
        else
        {
            Toast.makeText(this.Context, "Error connecting to server", Toast.LENGTH_SHORT);
        }
    }
}
