package com.example.z034.myirishlifeapp;


import android.app.Activity;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanner);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        mScannerView.resumeCameraPreview(this);
    }
}
