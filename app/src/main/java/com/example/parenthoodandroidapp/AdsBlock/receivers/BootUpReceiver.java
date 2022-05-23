package com.example.parenthoodandroidapp.AdsBlock.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.parenthoodandroidapp.AdsBlock.vpn.AutoLaunchActivity;


public class BootUpReceiver extends BroadcastReceiver {

    private SharedPreferences prefs;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            //TODO
            prefs = context.getSharedPreferences("com.example.cysandroidapp", Context.MODE_PRIVATE);

            int autoload = prefs.getInt("autoload", 0);
            if (autoload == 1){
                //autoload is on - start service
                Intent i = new Intent(context, AutoLaunchActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        }
    }
}