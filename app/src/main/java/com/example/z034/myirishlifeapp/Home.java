package com.example.z034.myirishlifeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class Home extends AppCompatActivity {


    private String userId;
    private String Pin;
    private JSONArray PolicyInfo = null;
    private GridLayout PolicyGrid;
    private Button addPolicy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        PolicyGrid = (GridLayout) findViewById(R.id.HomePolicyGrid);
        addPolicy = (Button) findViewById(R.id.AddPolicy);
        Intent intent = getIntent();
        this.userId = intent.getStringExtra(ApplicationConstants.Username);
        this.Pin = intent.getStringExtra(ApplicationConstants.Pin);
        HttpUtilities.GetPolicyData(userId, Pin, getApplicationContext(), intent, PolicyGrid);

    }

    public void AddPolicyButtonClick(View v) {
        Intent addPolicyIntent = new Intent(getApplicationContext(), AddPolicyActivity.class);
        addPolicyIntent.putExtra(ApplicationConstants.Username, userId);
        addPolicyIntent.putExtra(ApplicationConstants.Pin, Pin);
        startActivity(addPolicyIntent);
    }

}
