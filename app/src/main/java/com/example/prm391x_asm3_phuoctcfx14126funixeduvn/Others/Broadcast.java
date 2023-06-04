package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.IOException;

public class Broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            SharedPreferences sharedPreferences = context.getSharedPreferences("save_pref", Context.MODE_PRIVATE);
            if(sharedPreferences.getString(phoneNumber, null) != null && phoneNumber != null){
                String linkIcon = sharedPreferences.getString(phoneNumber, null);
                try {
                    Bitmap icon = BitmapFactory.decodeStream(context.getAssets().open(linkIcon));
                    CustomToast.makeText(context, icon, Toast.LENGTH_LONG);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
