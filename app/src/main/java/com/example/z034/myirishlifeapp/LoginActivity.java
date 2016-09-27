package com.example.z034.myirishlifeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.app.Dialog;
import android.view.Window;

public class LoginActivity extends AppCompatActivity {

    private EditText userPinField;
    private String UserId;
    private Dialog TermsAndConditionsDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPinField = (EditText) findViewById(R.id.LoginPin);
        Intent intent = getIntent();
        this.UserId = intent.getStringExtra(ApplicationConstants.Username);
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

    public void ForgottenPinButtonClick(View v)
    {
        Intent requestPinActivity = new Intent(getApplicationContext(), RequestPinResetActivity.class);
        this.startActivity(requestPinActivity);
    }

    public void TermsAndConditionsButtonClick(View v)
    {
        Dialog termsAndConditionsDialog = new Dialog(this);
        termsAndConditionsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        termsAndConditionsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout, null));
        this.TermsAndConditionsDialog = termsAndConditionsDialog;
        termsAndConditionsDialog.show();
    }

    public void dismissListener(View v)
    {
        this.TermsAndConditionsDialog.dismiss();
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
            HttpUtilities.AuthenticateUserWithPin(this.UserId, pin, userPinField, getApplicationContext());
        }
    }
}
