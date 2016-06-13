package com.zzptc.LiuXiaolong.baidu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.UpdateInfo;
import com.zzptc.LiuXiaolong.baidu.content.MyContents;
import com.zzptc.LiuXiaolong.baidu.fragment.NetStatusFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_user_center)
public class User_Center extends AppCompatActivity{
        @ViewInject(R.id.user_center_toolbar)
    private Toolbar toolbar;
        @ViewInject(R.id.version_update)
    private Button version_update;

    private NetStatusFragment netStatusFragment;
        @ViewInject(R.id.general_setting)
    private Button general_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
        initListener();

    }


    public void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("我的卫士");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);



    }


    public void initListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);

            }
        });

    }

    @Event(value = {R.id.version_update,R.id.general_setting},type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()){
            case R.id.version_update:
                startActivityForResult(new Intent(this,Load_Version.class), MyContents.REQUEST_CODE);
                break;
            case R.id.general_setting:
                startActivity(new Intent(this,General_Setting.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usercenter_ment,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutBodyguard:
                startActivity(new Intent(this,AboutBodyGuard.class));
                overridePendingTransition(R.anim.alpha_enter_orther_activity,R.anim.alpha_exit_this_activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MyContents.REQUEST_CODE){
            System.out.println(resultCode);
            switch (resultCode){
                case MyContents.NEED_DOWNLOAD:
                    if (data != null){
                        UpdateInfo updateInfo = (UpdateInfo) data.getSerializableExtra("info");
                        netStatusFragment = NetStatusFragment.newInstance(updateInfo.getVersion_info(), updateInfo);
                        netStatusFragment.show(getFragmentManager(),null);
                    }
                    break;
                case MyContents.NO_NETWORK:
                    showDialog("网络当前不可用，请检查设置");
                    break;
                case MyContents.NO_NEED_DOWNLOAD:
                    showDialog("已是最新版本");
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showDialog(String info){
        netStatusFragment = NetStatusFragment.newInstance(info);
        netStatusFragment.show(getFragmentManager(),null);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
