package com.example.z034.myirishlifeapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.example.z034.myirishlifeapp.R.id.bottomBar;
import static com.example.z034.myirishlifeapp.R.id.chat_list_view;



/**
 * Created by v584 on 21/09/2016.
 */

public class OnlineChat extends Activity {

   // ListView listview;
  //  EditText chat_text;
   // Button Send;
    boolean position=false;
    boolean startChat=true;
    boolean reply=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlinechat);


        final ListView listView = (ListView) findViewById(R.id.chat_list_view);
        final EditText chat_text = (EditText) findViewById(R.id.chatTxt);
        final Button Send = (Button) findViewById(R.id.send_button);
        final ChatAdapter adapter = new ChatAdapter(OnlineChat.this, R.layout.single_message);
        listView.setAdapter(adapter);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(adapter.getCount()-1);
            }
        });
        if (startChat){
            Date d=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("  MMMM d, yyyy HH:mm  ");
            String currentDateTimeString = sdf.format(d);
            chat_text.setText(currentDateTimeString);
            adapter.add(new DataProvider(position,chat_text.getText().toString()));
            chat_text.setText("");
            chat_text.setText("  Welcome to customer chat.  ");
            adapter.add(new DataProvider(position,chat_text.getText().toString()));
            chat_text.setText("");
            chat_text.setText("  My name is Bot.  ");
            adapter.add(new DataProvider(position,chat_text.getText().toString()));
            chat_text.setText("");
            chat_text.setText("  How may I help you?  ");
            adapter.add(new DataProvider(position,chat_text.getText().toString()));
            chat_text.setText("");
                }
        startChat=false;
        position=true;
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
                String currentDateTimeString = sdf.format(d);
                Send.setEnabled(false);
                adapter.add(new DataProvider(position,chat_text.getText().toString()+ "    " + currentDateTimeString + "\n"+ getEmijoByUnicode(0x2705)));
                position=!position;
                chat_text.setText("");
                position=false;

                Send.postDelayed(new Runnable(){
                    public void run() {
                        Date d=new Date();
                        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
                        String currentDateTimeString = sdf.format(d);
                        String[] array = getApplicationContext().getResources().getStringArray(R.array.BotChat);
                        String randomStr = array[new Random().nextInt(array.length)];
                        chat_text.setText(randomStr);
                        adapter.add(new DataProvider(position, chat_text.getText().toString()+ "    " + currentDateTimeString));
                        chat_text.setText("");
                        position = true;
                        Send.setEnabled(true);

                    }
                },900);

                }
            });



        BottomBar bottombar = (BottomBar) findViewById(bottomBar);
        bottombar.setDefaultTab(R.id.tab_chat);
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.tab_call) {
                    String phno = "tel:00353872411677";
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                    startActivity(i);
                }
                if (tabId == R.id.tab_email) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gunninmm@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }
                if (tabId == R.id.tab_request) {
                    new AlertDialog.Builder(OnlineChat.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(OnlineChat.this, "Your request has been logged. You will receive a call within x hours", Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(OnlineChat.this, "Request cancelled", Toast.LENGTH_LONG).show();
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

                if (tabId == R.id.tab_email) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gunninmm@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Customer Service Request");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have a question");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }
                if (tabId == R.id.tab_request) {
                    new AlertDialog.Builder(OnlineChat.this)
                            .setTitle("Request Call Back")
                            .setMessage("Do you wish to request a call back on this device?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(OnlineChat.this, "Your request has been logged. You will receive a call within x hours", Toast.LENGTH_LONG).show();
                                    // we can send details of where they are in the app currently at this point
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(OnlineChat.this, "Request cancelled", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });


    }
    public String getEmijoByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
