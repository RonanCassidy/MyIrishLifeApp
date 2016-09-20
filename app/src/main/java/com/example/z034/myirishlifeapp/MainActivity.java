package com.example.z034.myirishlifeapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void hellomark(String qqqq)
    {
        EditText text = (EditText) findViewById(R.id.editText);
        text.setText("I told you to leave me alone");
    }
}
