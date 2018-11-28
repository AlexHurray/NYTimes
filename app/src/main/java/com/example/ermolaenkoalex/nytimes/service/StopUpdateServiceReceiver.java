package com.example.ermolaenkoalex.nytimes.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StopUpdateServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, UpdateService.class);
        context.stopService(service);
    }
}
