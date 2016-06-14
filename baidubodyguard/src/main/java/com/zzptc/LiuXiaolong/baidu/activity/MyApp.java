package com.zzptc.LiuXiaolong.baidu.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.MemeryTools;
import com.zzptc.LiuXiaolong.baidu.fragment.InstalledApp_time;
import com.zzptc.LiuXiaolong.baidu.fragment.Installedapp;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_my_app)
public class MyApp extends AppCompatActivity {
    @ViewInject(R.id.viewpager_installed_app)
    private ViewPager viewpager_installed_app;
    @ViewInject(R.id.installed_count)
    private TextView installed_count;
    @ViewInject(R.id.btn_install_app)
    private Button btn_install_app;
    @ViewInject(R.id.btn_install_appTime)
    private Button btn_install_appTime;
    private MemeryTools memeryTools;

    private List<Fragment> listfragment;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
        initdata();
    }


    public void init(){
        memeryTools = new MemeryTools();
        //已安装应用数量
        installed_count.setText("已安装"+memeryTools.getInstallAppInfo(this).size()+"个应用");
    }

    public void initdata(){
        listfragment = new ArrayList<>();
        listfragment.add(new Installedapp());
        listfragment.add(new InstalledApp_time());
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listfragment.get(position);
            }

            @Override
            public int getCount() {
                return listfragment.size();
            }
        };

        viewpager_installed_app.setAdapter(adapter);
        viewpager_installed_app.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpager_installed_app.setCurrentItem(position);
                reset();
                selector(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //点击监听
    @Event(value = {R.id.btn_install_app,R.id.btn_install_appTime})
    private void getEvent(View v){
        reset();
        switch (v.getId()){
            case R.id.btn_install_app:
                selector(0);
                break;
            case R.id.btn_install_appTime:
                selector(1);
                break;
        }
    }


    private void selector(int i){
        switch (i){
            case 0:
                btn_install_app.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                btn_install_appTime.setTextColor(Color.parseColor("#ffffff"));

                break;
        }
        viewpager_installed_app.setCurrentItem(i);
    }

    private void reset(){
        btn_install_app.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btn_install_appTime.setTextColor(getResources().getColor(android.R.color.darker_gray));

    }
}
