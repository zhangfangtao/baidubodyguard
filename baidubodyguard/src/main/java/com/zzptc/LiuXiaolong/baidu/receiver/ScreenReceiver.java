package com.zzptc.LiuXiaolong.baidu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.zzptc.LiuXiaolong.baidu.service.LocationService;

public class ScreenReceiver extends BroadcastReceiver {
    public ScreenReceiver() {
    }
    private long screenOffTime;
    private long screenOnTime;
    private int count = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF)){
            screenOffTime = System.currentTimeMillis();
        }
        if (action.equals(Intent.ACTION_SCREEN_ON)){
            screenOnTime = System.currentTimeMillis();
        }

        if (screenOnTime - screenOffTime <1000){
            count ++;

            if (count ==4){
                count = 0;
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);

                context.startService(new Intent(context, LocationService.class));
            }
        }
    }
}
