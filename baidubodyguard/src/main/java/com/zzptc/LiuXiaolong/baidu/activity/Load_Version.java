package com.zzptc.LiuXiaolong.baidu.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.UpdateInfo;
import com.zzptc.LiuXiaolong.baidu.content.MyContents;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.dialogfragment_load__version)
public class Load_Version extends Activity {
    @ViewInject(R.id.iv_red)
    private ImageView iv_red;
    @ViewInject(R.id.iv_yellow)
    private ImageView iv_yellow;
    private AnimationSet red_animation,yellow_animation;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
        initListener();

    }

    public void init(){
        handler = new Handler();
        red_animation = (AnimationSet) AnimationUtils.loadAnimation(this,R.anim.load_animation);
        yellow_animation = (AnimationSet) AnimationUtils.loadAnimation(this,R.anim.load_animation);
        iv_red.setAnimation(red_animation);
        handler.postDelayed(checkNetWorkState,1000);

    }

    public void initListener(){
        red_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                iv_yellow.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_yellow.setVisibility(View.VISIBLE);
                iv_yellow.startAnimation(yellow_animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        yellow_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_red.startAnimation(red_animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    Runnable checkNetWorkState = new Runnable() {
        @Override
        public void run() {
            boolean flag = false;
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            if (manager.getActiveNetworkInfo() != null){
                flag = manager.getActiveNetworkInfo().isAvailable();
            }
            if (flag){
                //有网络
                RequestParams params = new RequestParams("http://10.0.2.2:8080/BaiduSafe/servlet/MyServlet");
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        UpdateInfo info = new Gson().fromJson(result,UpdateInfo.class);

                        try {
                            int serverVersionCode = info.getVersion_code();
                            int clientVersionCode = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS).versionCode;

                            if (serverVersionCode > clientVersionCode){
                                Intent intent = new Intent();
                                intent.putExtra("info",info);
                                setResult(MyContents.NEED_DOWNLOAD,intent);
                                finish();
                            }else{
                                setResult(MyContents.NO_NEED_DOWNLOAD);
                                finish();
                            }

                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        setResult(MyContents.DOWN_ERROR);
                        finish();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        setResult(MyContents.DOWN_CANCELLED);
                        finish();
                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }else{
                //无网络
                setResult(MyContents.NO_NETWORK);
                finish();
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
        }
            finish();
        return super.onKeyDown(keyCode, event);
    }
}
