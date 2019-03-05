package com.example.qjh.r.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"接受成功",Toast.LENGTH_SHORT).show();
    }

    public MyBroadcastReceiver() {

    }
}
