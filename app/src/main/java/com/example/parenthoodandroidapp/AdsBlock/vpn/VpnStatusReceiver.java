package com.example.parenthoodandroidapp.AdsBlock.vpn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class VpnStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        onVpnStartReceived();
    }

    public void onVpnStartReceived(){

    }
}