package com.example.z034.myirishlifeapp;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class FirstTimeLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_login);
        if(AccountUtilities.CheckForAccount(this, this))
        {
            Account acc = AccountUtilities.GetSavedAccount(this, this);
            String userId = acc.name;
            String password = AccountUtilities.GetPasswordFromAccount(this, acc);
            // Already contains an account. Don't show password login screen show pin login
            Intent PinLogin = new Intent(getApplicationContext(), LoginActivity.class);
            PinLogin.putExtra(ApplicationConstants.Username, userId);
            PinLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(PinLogin);
        }
    }

    public void ScanButtonClick(View v)
    {
        // Do something
    }

    public void RegisterButtonClick(View v)
    {
        Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
        this.startActivity(registerActivity);
    }

    public void SubmitButtonClick(View v)
    {
        // Do Something
        this.AttemptLogin();
    }

    private void AttemptLogin()
    {
        View focusView = null;

        EditText userNameField = (EditText) findViewById(R.id.FirstTimeLoginUserId);
        EditText passwordField = (EditText) findViewById(R.id.FirstTimeLoginPassword);
        userNameField.setError(null);
        passwordField.setError(null);

        String username = userNameField.getText().toString();
        String password = passwordField.getText().toString();
        boolean cancel = false;

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
        {
            if(TextUtils.isEmpty(username))
            {
                userNameField.setError("User Id is required");
                focusView = userNameField;
            }

            if(TextUtils.isEmpty(password))
            {
                passwordField.setError("Password is required");
                focusView = passwordField;
            }

            cancel = true;
        }

        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            HttpUtilities.AuthenticateUserWithPassword(username, password, getApplicationContext(), getIntent());
        }
    }
}
