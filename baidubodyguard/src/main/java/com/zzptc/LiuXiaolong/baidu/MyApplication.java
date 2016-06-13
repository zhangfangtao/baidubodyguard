package com.zzptc.LiuXiaolong.baidu;

import android.app.Application;
import android.os.Build;

import org.xutils.x;

/**
 * Created by lxl97 on 2016/4/25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
