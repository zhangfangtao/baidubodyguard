package com.zzptc.LiuXiaolong.baidu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.zzptc.LiuXiaolong.baidu.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lxl97 on 2016/4/13.
 */
@ContentView(R.layout.activity_treasure_box)
public class Treasure_box extends Activity{
        @ViewInject(R.id.return_home)
        private Button return_home;
        @ViewInject(R.id.app_manager)
        private Button app_manager;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
        }

    @Event(value = {R.id.return_home,R.id.app_manager})
    private void getEvent(View v){
        switch (v.getId()){
            case R.id.return_home:
                finish();
                overridePendingTransition(R.anim.mainui,R.anim.exit_treasure);
                break;
            case R.id.app_manager:
                //跳转到应用管理页面
                startActivity(new Intent(this,AppManager.class));
                overridePendingTransition(R.anim.alpha_enter_orther_activity,R.anim.alpha_exit_this_activity);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.mainui,R.anim.exit_treasure);
        }
        return super.onKeyDown(keyCode, event);
    }
}
