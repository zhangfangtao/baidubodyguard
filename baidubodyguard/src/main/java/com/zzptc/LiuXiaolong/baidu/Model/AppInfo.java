package com.zzptc.LiuXiaolong.baidu.Model;

import android.graphics.drawable.Drawable;

import java.util.Date;

/**
 * Created by lxl97 on 2016/6/8.
 */
public class AppInfo {
    private String appName;
    private String versionName;
    private String packageName;
    private Drawable icon;
    private int versionCode;
    private Date firstInstallTime;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Date getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(Date firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }
}
