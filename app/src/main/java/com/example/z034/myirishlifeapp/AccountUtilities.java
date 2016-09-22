package com.example.z034.myirishlifeapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.Manifest.permission.GET_ACCOUNTS;

public class AccountUtilities {
    public static final String accountType = "com.example.x339.myapplication.DEMOACCOUNT";
    private static final int REQUEST_GET_ACCOUNTS = 0;

    public static boolean CheckForAccount(Context context, Activity activity)
    {
        AccountManager accountManager = AccountManager.get(context);
        if (ContextCompat.checkSelfPermission(context, GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED)
        {
            Account[] accounts = accountManager.getAccountsByType(accountType);
            return accounts.length == 0;
        }
        else
        {
            ActivityCompat.requestPermissions(activity, new String[]{GET_ACCOUNTS}, REQUEST_GET_ACCOUNTS);
            return false;
        }
    }

    public static Account GetSavedAccount(Context context, Activity activity)
    {
        AccountManager accountManager = AccountManager.get(context);
        if (ContextCompat.checkSelfPermission(context, GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED)
        {
            Account[] accounts = accountManager.getAccountsByType(accountType);
            if(accounts.length == 0)
            {
                return accounts[0];
            }
        }
        else
        {
            ActivityCompat.requestPermissions(activity, new String[]{GET_ACCOUNTS}, REQUEST_GET_ACCOUNTS);
        }
        return null;
    }

    public static boolean AddAccount(Context context, String email, String password)
    {
        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(email, accountType);
        boolean success = accountManager.addAccountExplicitly(account, password, null);
        if (success) {
            Log.d("My Irish Life app", "Account created");
        } else {
            Log.d("My Irish Life app", "Account creation failed. Look at previous logs to investigate");
        }
        return success;
    }
}
