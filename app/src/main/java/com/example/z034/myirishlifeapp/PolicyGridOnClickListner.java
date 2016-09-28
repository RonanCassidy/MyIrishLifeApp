package com.example.z034.myirishlifeapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class PolicyGridOnClickListner implements View.OnClickListener {

    private String UserId;
    private String Pin;
    private String PolicyId;
    private android.content.Context Context;
    private android.content.Intent Intent;
    public PolicyGridOnClickListner(String userId, String pin, String policyId, Context context, Intent intent)
    {
        this.UserId = userId;
        this.Pin = pin;
        this.PolicyId = policyId;
        this.Context = context;
        this.Intent = intent;
    }

    @Override
    public void onClick(View v)
    {
        //Do something
    }
}
