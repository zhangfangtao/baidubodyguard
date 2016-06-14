package com.zzptc.LiuXiaolong.baidu.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zzptc.LiuXiaolong.baidu.MainActivity;
import com.zzptc.LiuXiaolong.baidu.Model.AppInfo;
import com.zzptc.LiuXiaolong.baidu.Model.Contacts;
import com.zzptc.LiuXiaolong.baidu.Model.TaskInfo;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.ImportDB;
import com.zzptc.LiuXiaolong.baidu.Tools.MemeryTools;
import com.zzptc.LiuXiaolong.baidu.Tools.Tools;
import com.zzptc.LiuXiaolong.baidu.Utils.Read_Contacts;
import com.zzptc.LiuXiaolong.baidu.receiver.ScreenReceiver;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl97 on 2016/4/12.
 */
public class Welcome extends Activity implements Runnable {
    private boolean isFirstUse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        Handler handler = new Handler();
        handler.postDelayed(this,2000);

    }

    @Override
    public void run() {

            Animation animation = AnimationUtils.loadAnimation(this,R.anim.welcome);
            ImageView img = (ImageView) findViewById(R.id.welcome_center);
            img.setAnimation(animation);


        SharedPreferences preferences = getSharedPreferences("isFirstUse",MODE_PRIVATE);
        isFirstUse = preferences.getBoolean("isFirstUse",true);
        if (isFirstUse){

            startActivity(new Intent(this, MainActivity.class));
        }else{

            startActivity(new Intent(this,MainUI_Activity.class));

        }
        overridePendingTransition(R.anim.transition2,R.anim.exit_welcome);

        finish();

        x.task().run(new Runnable() {
            @Override
            public void run() {
                ImportDB db = new ImportDB();
                boolean flag = db.importMobileDb(Welcome.this);
                ArrayList<Contacts> list = Read_Contacts.getList();
                if (list == null){
                    Read_Contacts read = new Read_Contacts(Welcome.this);
                    list = read.getAllContacts();

                    Read_Contacts.setList((ArrayList<Contacts>) list);
                }

                ArrayList<AppInfo> appinfolist = MemeryTools.getAppInfolist();
                if (appinfolist == null){
                    MemeryTools memeryTools = new MemeryTools();
                    appinfolist = memeryTools.getInstallAppInfo(Welcome.this);

                    MemeryTools.setAppInfolist(appinfolist);
                }

            }
        });

        preferences.edit().putBoolean("isFirstUse",false).commit();

    }


}
