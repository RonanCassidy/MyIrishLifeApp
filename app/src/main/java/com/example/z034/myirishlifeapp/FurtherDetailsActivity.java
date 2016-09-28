package com.example.z034.myirishlifeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by v584 on 27/09/2016.
 */

public class FurtherDetailsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furtherdetails);
        ImageView img=(ImageView) findViewById(R.id.imagefurtherdetails);
        Bundle extras = getIntent().getExtras();
        int PlanType = extras.getInt("PlanType");

        if (PlanType==1){
            img.setImageResource(R.mipmap.ppi);
        }
        if (PlanType==2){
            img.setImageResource(R.mipmap.health);
        }
        if (PlanType==3){
            img.setImageResource(R.mipmap.cloas);
        }
        if (PlanType==4){
            img.setImageResource(R.mipmap.health);
        }

   }
}