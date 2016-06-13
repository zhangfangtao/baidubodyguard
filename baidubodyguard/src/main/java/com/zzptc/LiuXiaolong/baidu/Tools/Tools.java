package com.zzptc.LiuXiaolong.baidu.Tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.view.inputmethod.InputMethodManager;

import com.github.premnirmal.textcounter.CounterView;
import com.zzptc.LiuXiaolong.baidu.Model.TaskInfo;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.processes.ProcessManager;
import com.zzptc.LiuXiaolong.baidu.processes.models.AndroidAppProcess;


import org.xutils.DbManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lxl97 on 2016/4/30.
 */
public class Tools {


    /**
     * 验证手机号是否正确
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                        .compile("^1[34578]\\d{9}$");


//                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 关闭键盘
     */
    public static void closeKeyboard(Context context ){
        //点击确认后关闭软键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive()) {
            //如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }


    //随机数组中的一种颜色
    public static String  getRandColorCode(){
        String [] color = {"#82B647","#0187D0","#E81D62","#009587","#679E37","#9B26AF","#FE5621","#4184F3","#EE6B00","#3E50B4","#747474","#9B26AF","#6639B6"};
        int colorleng = color.length;
        Random randomcolor = new Random();
        int colorindex = randomcolor.nextInt(colorleng-1);
        String col = color[colorindex];
        return col;
    }

    public static Boolean sendSMS(String phoneNumber, String message) {
        // 获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
            return true;
        }
        return false;
    }


    public static void CounterViewUp(CounterView counterView, long endValue, float increment ){
        String size = MemeryTools.convertStorage(endValue);
        String[] str = size.split(" ");
        counterView.setAutoStart(true);
        counterView.setStartValue(0);
        counterView.setEndValue(Float.parseFloat(str[0]));
        counterView.setIncrement(increment);
        counterView.setTimeInterval(1);
        counterView.start();

    }
    public static void CounterViewDown(CounterView counterView,long startValue, long endValue, float increment ){
        String size1 = MemeryTools.convertStorage(startValue);
        String size = MemeryTools.convertStorage(endValue);
        String[] str1 = size.split(" ");
        String[] str2 = size1.split(" ");
        counterView.setAutoStart(true);
        counterView.setStartValue(Float.parseFloat(str2[0]));
        counterView.setEndValue(Float.parseFloat(str1[0]));
        counterView.setIncrement(increment);
        counterView.setTimeInterval(1);
        counterView.start();

    }


}
