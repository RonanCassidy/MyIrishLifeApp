package com.example.z034.myirishlifeapp;



import android.content.Intent;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mScannerView.stopCamera();
    }
    @Override
    public void handleResult(Result result)
    {
        Log.v("handle result", result.getText());

        Intent data = new Intent();
        data.putExtra("qrcode_details", result.getText());
        setResult(CommonStatusCodes.SUCCESS, data);
        mScannerView.stopCamera();
        finish();
    }
}
