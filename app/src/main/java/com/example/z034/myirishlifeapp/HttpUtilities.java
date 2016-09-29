package com.example.z034.myirishlifeapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.app.*;
import android.widget.EditText;
import android.widget.GridLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class HttpUtilities {

    public static final String AuthenticateServerUrl = "http://52.174.106.218/AutService.asmx/";
    public static final String AzureServerUrl = "http://10.233.204.232:9090/AutService.asmx/";
    public static final String AuthenticateWithPin = "AuthenticateWithPin";
    public static final String GetUserPolicyDetails = "GetUserPolicyDetails";
    public static final String AuthenticateWithPassword = "AuthenticateWithPassword";
    public static final String GetUserPolicyInfo = "GetUserPolicyList";
    public static final String StoreUserDevice = "StoreUserDevice";
    public static final String Mongo = "userID=Yvonne.Mongo856&deviceID=";


    public static void AuthenticateUserWithPassword(String username, String password, Context context, Intent intent)
    {
        UserLoginPassword loginWithPassword = new UserLoginPassword(AuthenticateServerUrl, AuthenticateWithPassword, username, password, context, intent);
        loginWithPassword.execute((Void) null);
    }

    public static void AuthenticateUserWithPin(String username, String pin, EditText pinField, Context context)
    {
        UserLoginPin loginWithPin = new UserLoginPin(AuthenticateServerUrl, AuthenticateWithPin, username, pin, pinField, context);
        loginWithPin.execute((Void) null);
    }

    public static void RetreivePolicyDetail(String username, String pin, String policyID, Context context)
    {
        String query = "userID=" + username + "&" + "pin=" + pin+ "&" + "policyID="+policyID;
        GetPolicyDetails Singlepolicydetail = new GetPolicyDetails(AuthenticateServerUrl, GetUserPolicyDetails, query, policyID, context);
        Singlepolicydetail.execute((Void) null);
    }


    public static void GetPolicyData(String userId, String pin, Context context, Intent intent, GridLayout PolicyGrid)
    {
        GetUserPolicyInformation getNewUserInfoTask = new GetUserPolicyInformation(AuthenticateServerUrl, GetUserPolicyInfo, userId, pin, context, intent, PolicyGrid);
        getNewUserInfoTask.execute((Void) null);
    }

    public static void SendDeviceTokensToServer(String token)
    {
        ProcessDeviceToken processDeviceToken = new ProcessDeviceToken(AuthenticateServerUrl, StoreUserDevice, Mongo, token);
        processDeviceToken.execute((Void) null);
    }

    public static String GetServerResponse(URL myurl) {
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
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean GetAuthenticatedYesNo(String serverResponse)
    {
        JSONObject response = null;
        try {
            response = new JSONObject(serverResponse);
            if(response.get("authenticated").toString() == "true") return true;
            else return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}