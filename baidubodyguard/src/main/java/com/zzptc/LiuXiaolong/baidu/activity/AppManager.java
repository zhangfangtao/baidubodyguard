package com.zzptc.LiuXiaolong.baidu.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.premnirmal.textcounter.CounterView;
import com.zzptc.LiuXiaolong.baidu.Model.AppInfo;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.Anim;
import com.zzptc.LiuXiaolong.baidu.Tools.MemeryTools;
import com.zzptc.LiuXiaolong.baidu.Tools.Tools;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_app_manager)
public class AppManager extends AppCompatActivity {
    @ViewInject(R.id.install_app_count)
    private CounterView installAppCount;
    @ViewInject(R.id.app_manager_use_memory_info)
    private TextView memory_use_info;
    @ViewInject(R.id.app_manager_bar)
    private ProgressBar bar;
    @ViewInject(R.id.my_app)
    private LinearLayout my_app;


    private MemeryTools memeryTools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
    }


    public void init(){

        memeryTools = new MemeryTools();
        String temp = memory_use_info.getText().toString();

        //已使用的ROM空间
        String usememory = MemeryTools.convertStorage(memeryTools.getTotalInternalStorgeSize()-memeryTools.getAvailableInternalStorgeSize());

        memory_use_info.setText(temp+usememory+"/"+MemeryTools.convertStorage(memeryTools.getTotalInternalStorgeSize()));

        long use = memeryTools.getTotalInternalStorgeSize()-memeryTools.getAvailableInternalStorgeSize();
        double memory = (double) use/(double) memeryTools.getTotalInternalStorgeSize();

        //使用内存占总内存的百分比进度条
        bar.setMax(100);
        bar.setProgress((int)(memory*100));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置counterview显示安装的应用数量
        installAppCount.setStartValue(0);
        installAppCount.setAutoStart(true);
        installAppCount.setEndValue(memeryTools.getInstallAppInfo(this).size());
        installAppCount.setIncrement(1f);
        installAppCount.setTimeInterval(1);
    }


    @Event(value = R.id.my_app)
    private void getEvent(View v){
        switch (v.getId()){
            case R.id.my_app:
                startActivity(new Intent(this, MyApp.class));
                //activity跳转动画
                Anim.enterActivityAnim(this);
                break;
        }
    }




    //返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
        }
        return super.onKeyDown(keyCode, event);
    }



}
