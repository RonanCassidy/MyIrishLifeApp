package com.example.z034.myirishlifeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v584 on 22/09/2016.
 */

    public class ChatAdapter extends ArrayAdapter<DataProvider> {

    private List<DataProvider> chat_list = new ArrayList<DataProvider>();
    private TextView CHAT_TXT;
    Context CTX;
    public ChatAdapter(Context context, int resource) {
        super(context, resource);
        CTX=context;
    }

    @Override
    public void add(DataProvider object) {
        chat_list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return chat_list.size();
    }

    @Nullable
    @Override
    public DataProvider getItem(int position) {
        return chat_list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) CTX.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.single_message,parent,false);
        }
        CHAT_TXT=(TextView) convertView.findViewById(R.id.SingleMessage);
        String Message;
        boolean POSITION;
        DataProvider provider=getItem(position);
        Message=provider.message;
        POSITION=provider.position;
        CHAT_TXT.setText(Message);
        CHAT_TXT.setBackgroundResource(POSITION ? R.drawable.right : R.drawable.left);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if(!POSITION){
            params.gravity= Gravity.LEFT;
        }
        else{
            params.gravity= Gravity.RIGHT;
        }
        CHAT_TXT.setLayoutParams(params);
        return convertView;
    }
}
