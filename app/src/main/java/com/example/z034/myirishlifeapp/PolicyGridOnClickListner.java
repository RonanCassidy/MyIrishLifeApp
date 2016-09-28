package com.example.z034.myirishlifeapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

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
        Intent addPolicyIntent = new Intent(this.Context, PolicyDetailsActivity.class);
        addPolicyIntent.putExtra("PolicyNumber",PolicyId);
        addPolicyIntent.putExtra(ApplicationConstants.Pin,this.Pin);
        addPolicyIntent.putExtra(ApplicationConstants.Username,this.UserId);
        addPolicyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.Context.startActivity(addPolicyIntent);
    }
}
