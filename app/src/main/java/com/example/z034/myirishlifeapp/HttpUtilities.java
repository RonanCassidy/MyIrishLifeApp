package com.example.z034.myirishlifeapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.app.*;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class HttpUtilities {

    public static final String AuthenticateServerUrl = "http://10.233.204.232:9090/AutService.asmx/";
    public static final String AuthenticateWithPin = "AuthenticateWithPin";
    public static final String AuthenticateWithPassword = "AuthenticateWithPassword";

    public static void AuthenticateUserWithPassword(String username, String password)
    {
        String query = "userID=" + username + "&" + "password=" + password;
    }

    public static void AuthenticateUserWithPin(String username, String pin, EditText pinField, Context context)
    {
        String query = "userID=" + username + "&" + "pin=" + pin;
        UserLoginPin loginWithPin = new UserLoginPin(AuthenticateServerUrl, AuthenticateWithPin, query, pinField, context);
        loginWithPin.execute((Void) null);
    }
}