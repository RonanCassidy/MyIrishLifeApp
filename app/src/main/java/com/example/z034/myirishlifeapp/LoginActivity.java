package com.example.z034.myirishlifeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText userPinField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPinField = (EditText) findViewById(R.id.LoginPin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void LoginButtonClick(View v)
    {
        // Do something
        // Validate pin with middle tier services
        try
        {
            this.attemptLogin();
        }
        catch(Exception e)
        {
            String s1 = e.getMessage();
        }
    }

    public void RegisterButtonClick(View v)
    {
        // Do something
        // Do nothing?
    }

    public void ForgottenPinButtonClick(View v)
    {
        // Do something
        // Do nothing?
    }

    private void attemptLogin()
    {
        userPinField.setError(null);
        String pin = userPinField.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(pin) == true)
        {
            userPinField.setError(getString(R.string.Login_error_empty_pin));
            focusView = userPinField;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // use pin
            HttpUtilities.AuthenticateUserWithPin("jnolan", pin, userPinField, getApplicationContext());
        }
    }
}
