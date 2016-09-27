package com.example.z034.myirishlifeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Function;

/**
 * Created by s970 on 21/09/2016.
 */

public class UserDetailActivity extends AppCompatActivity {
    boolean onStart=true;
    private static final String TAG_NAME = "name";
    private static final String TAG_DOB = "dob";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_MOBILE = "mobile";
    private static final String TAG_ADDRESS = "address";

    private JSONObject jsonobject;
    private EditText name, dob, email, phone, address, eircode;
    private TextView eircodeView;
    private Button update, save, cancel,useeircode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);

        BottomBar bottombar =(BottomBar) findViewById(R.id.bottomBar);
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_chat && !onStart) {
                    Intent i = new Intent(UserDetailActivity.this, OnlineChat.class);
                    startActivity(i);
                }
                if (tabId == R.id.tab_call && !onStart) {
                    String phno="tel:00353872411677";
                    Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                    startActivity(i);
                }
                if (tabId == R.id.tab_email && !onStart) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"gunninmm@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }
                if (tabId == R.id.tab_request && !onStart) {
                    new android.support.v7.app.AlertDialog.Builder(UserDetailActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserDetailActivity.this,"Your request has been logged. You will receive a call within x hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserDetailActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });
        bottombar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_chat && !onStart) {
                    Intent i = new Intent(UserDetailActivity.this, OnlineChat.class);
                    startActivity(i);
                }
                if (tabId == R.id.tab_call) {
                    String phno="tel:00353872411677";
                    Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                    startActivity(i);
                }
                if (tabId == R.id.tab_email) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"gunninmm@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }
                if (tabId == R.id.tab_request && !onStart) {
                    new android.support.v7.app.AlertDialog.Builder(UserDetailActivity.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserDetailActivity.this,"Your request has been logged. You will receive a call within x hours",Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserDetailActivity.this,"Request cancelled",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        onStart = false;

        name = (EditText) findViewById(R.id.name);
        dob = (EditText) findViewById(R.id.dob);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);
        eircode = (EditText) findViewById(R.id.eircode);

        eircodeView = (TextView) findViewById(R.id.eircodeView);

        update = (Button) findViewById(R.id.update);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        useeircode = (Button) findViewById(R.id.useeircode);

        // disable save/cancel
        disableEditing();

        // get user details
        String strJson="{\"username\": \"tkavanagh\",\n" +
                "\"password\": \"password13\",\n" +
                "\"pin\": \"1234\",\n" +
                "\"name\": \"Tricia Kavanagh\",\n" +
                "\"dob\": \"1980-01-01\",\n" +
                "\"email\": \"tricia.kavanagh@irishlife.ie\",\n" +
                "\"mobile\": \"+353870000000\",\n" +
                "\"address\": \"Something something something, \n" +
                "Slane, \n" +
                "Co. Meath\"}";

        // populate form fields
        try {
            jsonobject = new JSONObject(strJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            name.setText(jsonobject.getString(TAG_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            dob.setText(jsonobject.getString(TAG_DOB));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            email.setText(jsonobject.getString(TAG_EMAIL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            phone.setText(jsonobject.getString(TAG_MOBILE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            address.setText(jsonobject.getString(TAG_ADDRESS));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateClick(View v)
    {
        enableEditing();
    }

    public void saveClick(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm Update");
        builder.setMessage("These changes will be made to all your Irish Life policies");
        builder.setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // save to db ...
                disableEditing();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void cancelClick(View v)
    {
        finish();
    }

    public void useEircodeClick(View v)
    {
        // check eircodes...
    }

    private void enableEditing(){
        update.setEnabled(false);
        update.setVisibility(View.GONE);
        save.setEnabled(true);
        save.setVisibility(View.VISIBLE);
        cancel.setEnabled(true);
        cancel.setVisibility(View.VISIBLE);
        email.setEnabled(true);
        phone.setEnabled(true);
        address.setEnabled(true);
        eircode.setVisibility(View.VISIBLE);
        eircodeView.setVisibility(View.VISIBLE);
        useeircode.setEnabled(true);
        useeircode.setVisibility(View.VISIBLE);
    }

    private void disableEditing(){
        update.setEnabled(true);
        update.setVisibility(View.VISIBLE);
        save.setEnabled(false);
        save.setVisibility(View.GONE);
        cancel.setEnabled(false);
        cancel.setVisibility(View.GONE);
        eircodeView.setVisibility(View.GONE);
        useeircode.setEnabled(false);
        useeircode.setVisibility(View.GONE);
        eircode.setVisibility(View.GONE);
        email.setEnabled(false);
        phone.setEnabled(false);
        address.setEnabled(false);
    }


    public String getUserDetails(){
        String sName=name.getText().toString();
        return sName;
    }

}
