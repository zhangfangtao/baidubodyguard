package com.zzptc.LiuXiaolong.baidu.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.content.MyContents;
import com.zzptc.LiuXiaolong.baidu.receiver.ScreenReceiver;
import com.zzptc.LiuXiaolong.baidu.view.DanceWageTimer;
import com.zzptc.LiuXiaolong.baidu.view.RatingBar;
import com.zzptc.LiuXiaolong.baidu.view.RatingView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Random;
    @ContentView(R.layout.activity_mainui)
public class MainUI_Activity extends AppCompatActivity{
        @ViewInject(R.id.treasure_box)
    private Button treasure_box;
        @ViewInject(R.id.mobile_speedup)
    private Button mobile_speedup;
        @ViewInject(R.id.garbage_clean)
    private Button garbage_clean;
        @ViewInject(R.id.toolbar)
    private Toolbar toolbars;
        @ViewInject(R.id.tv_rate)
    private TextView tv_rate;
        @ViewInject(R.id.rb_rate)
    private RatingView ratingView;

    private RatingBar sec_bar,flu_bar,clear_bar;
        @ViewInject(R.id.rate)
    private RelativeLayout rate;
        @ViewInject(R.id.functionitems)
    private LinearLayout functionitems;
        @ViewInject(R.id.parent_protect)
    private Button parent_protect;
    private ScreenReceiver screenReceiver;

    int sec_rate = 1 + new Random().nextInt(10);
    int flu_rate = 1+ new Random().nextInt(10);
    int clear_rate = 1 + new Random().nextInt(10);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        init();
        initdata();
    }

    public void init(){
        setSupportActionBar(toolbars);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbars.setTitle(R.string.app_name);
        toolbars.setTitleTextColor(Color.WHITE);
        screenReceiver = new ScreenReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        //注册屏幕监听
        registerReceiver(screenReceiver,intentFilter);
    }

    public void initdata(){
        //计分 安全50%、流畅30%、清洁20%
        int total_wage = (int)(sec_rate * 0.5 + flu_rate * 0.3 + clear_rate * 0.2) * 10;
        DanceWageTimer danceWageTimer = new DanceWageTimer(DanceWageTimer.getTotalExecuteTime(total_wage,50),50,tv_rate,total_wage);
        danceWageTimer.start();

        String sec = sec_rate>=8?"安全度高":(sec_rate>=4?"安全度中":"安全度差");
        String flu = flu_rate>=8?"流畅度高":(flu_rate>=4?"流畅度中":"流畅度差");
        String clear = clear_rate>=8?"清洁度高":(clear_rate>=4?"清洁度中":"清洁度差");

        sec_bar = new RatingBar(sec_rate,sec);
        flu_bar = new RatingBar(flu_rate,flu);
        clear_bar = new RatingBar(clear_rate,clear);

        ratingView.addRatingBar(sec_bar);
        ratingView.addRatingBar(flu_bar);
        ratingView.addRatingBar(clear_bar);

        ratingView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ratingView.show();
            }
        },DanceWageTimer.getTotalExecuteTime(total_wage,50));


    }


    @Event(value = {R.id.treasure_box,R.id.mobile_speedup,R.id.garbage_clean,R.id.parent_protect})
    private void getEvent(View v){
        switch (v.getId()){
            case R.id.treasure_box:
                //开启百宝箱
                startActivity(new Intent(MainUI_Activity.this, Treasure_box.class));
                overridePendingTransition(R.anim.open_treasure1,R.anim.mainui);

                break;
            case R.id.mobile_speedup:
                startActivity(new Intent(this,Mobile_speedup.class));
                break;
            case R.id.garbage_clean:
                break;
            case R.id.parent_protect:

                //计分控件和功能模块退出动画
                Animation rate = AnimationUtils.loadAnimation(this,R.anim.rate_up);
                Animation tools = AnimationUtils.loadAnimation(this,R.anim.toolsitems_down);
                ratingView.startAnimation(rate);
                functionitems.startAnimation(tools);

                rate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivityForResult(new Intent(MainUI_Activity.this,Parent_protect.class), MyContents.ENTER_OTHER_ACTIVITY_CODE);
                        //进入其他页面动画
                        enterOtherActivityAnim();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
        }
    }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == MyContents.ENTER_OTHER_ACTIVITY_CODE){
                System.out.println(resultCode);
                switch (resultCode){
                    case MyContents.EXIT_ACTIVITY:
                        exitOtherActivityAnim();
                        break;
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

        /*
            Toolbar菜单
             */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /*
    toolbar菜单监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_center:
                startActivity(new Intent(this,User_Center.class));
                overridePendingTransition(R.anim.alpha_enter_orther_activity,R.anim.alpha_exit_this_activity);
            break;
        }
        return super.onOptionsItemSelected(item);
    }

        /**
         * 从其他页面进入时计分控件和功能模块的进入动画
         */
        private void exitOtherActivityAnim(){
            Animation rate = AnimationUtils.loadAnimation(this,R.anim.rate_down);
            Animation tools = AnimationUtils.loadAnimation(this,R.anim.toolsitems_up);
            ratingView.startAnimation(rate);
            functionitems.startAnimation(tools);
        }

        /**
         * 进入其他功能模块动画
         */
        public void enterOtherActivityAnim(){
            overridePendingTransition(R.anim.alpha_enter_orther_activity,R.anim.alpha_exit_this_activity);

        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (screenReceiver != null){
                unregisterReceiver(screenReceiver);
            }
        }
    }
