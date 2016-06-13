package com.zzptc.LiuXiaolong.baidu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.content.MyContents;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_parent_protect)
public class Parent_protect extends AppCompatActivity {
    @ViewInject(R.id.parent_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.onekey)
    private RelativeLayout onekey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
        initListener();
    }
    public void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void initListener(){
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              setResult(MyContents.EXIT_ACTIVITY);
              finish();
          }
      });


    }
    @Event(value = R.id.onekey)
    private void getEvent(View v){
        switch (v.getId()){
            case R.id.onekey:
                startActivityForResult(new Intent(this,OneKey_for_help.class),MyContents.ENTER_OTHER_ACTIVITY_CODE);
                overridePendingTransition(R.anim.alpha_enter_orther_activity,R.anim.alpha_exit_this_activity);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(MyContents.EXIT_ACTIVITY);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
