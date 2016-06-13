package com.zzptc.LiuXiaolong.baidu.activity;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.premnirmal.textcounter.CounterView;
import com.zzptc.LiuXiaolong.baidu.Adapter.MyRecyclerViewAdapter;
import com.zzptc.LiuXiaolong.baidu.Model.TaskInfo;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.MemeryTools;
import com.zzptc.LiuXiaolong.baidu.Tools.Tools;
import com.zzptc.LiuXiaolong.baidu.fragment.DialogFragment_speedup_appinfo;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_mobile_speedup)
public class Mobile_speedup extends AppCompatActivity implements MemeryTools.OnScanProcessListener,MyRecyclerViewAdapter.OnItemClickListener,View.OnClickListener{
    @ViewInject(R.id.mobile_speedup_toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.rv_app_list)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.tv_runningAppCount)
    private TextView tv_runningAppCount;
    @ViewInject(R.id.appbarlayout)
    private AppBarLayout appBarLayout;

    private MyRecyclerViewAdapter adapter;

    @ViewInject(R.id.mobile_speedup_title)
    private TextView mobile_speedup_title;


    //扫描进度textview
    @ViewInject(R.id.scanProgress)
    private TextView scanProgress;

    @ViewInject(R.id.counterview)
    private CounterView counterView;
    //扫描进度view
    @ViewInject(R.id.scanProgressView)
    private RelativeLayout scanProgressView;
    @ViewInject(R.id.progressList)
    private RelativeLayout progressList;
    @ViewInject(R.id.onekey_clearRunningApp)
    private Button onekey_clearRunningApp;
    private DialogFragment_speedup_appinfo dialogFragment_speedup_appinfo;


    long progressSize = 0;

    private DbManager dbManager;
    ArrayList<TaskInfo> taskInfos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
        initListener();
    }

    public void init(){
        //setting toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        MemeryTools memeryTools = new MemeryTools();
        memeryTools.setOnScanProcessListener(this);
        memeryTools.getRunningAppInfo();

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("whitelist.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().beginTransaction();
                    }
                });
        dbManager = x.getDb(daoConfig);

    }

    public void initListener(){
        //toolbar返回按钮监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //关闭activity动画
                overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);

            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset < -358){
//                    String str = clear_size.getText().toString()+"MB可清理";
//                    mobile_speedup_title.setText(str);
                }else{
                    mobile_speedup_title.setText("手机加速");
                }
            }
        });

        adapter = new MyRecyclerViewAdapter(taskInfos,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        onekey_clearRunningApp.setOnClickListener(this);
    }
    //读取内存中的应用监听
    @Override
    public void OnScanStartListener(Context context) {
    }

    @Override
    public void OnScanCurrentListener(Context context, int current, int total) {
        scanProgress.setText("正在扫描"+current+"/"+total);
    }

    @Override
    public void OnScanCompleteListener(Context context, final List<TaskInfo> list) {
        //扫描进度条隐藏   列表显示
        scanProgressView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        progressList.setVisibility(View.VISIBLE);
        progressSize = 0;

        try {
        for (TaskInfo info : list){
            if (!info.isSystem() && info.getAppName()!= null  && !info.isCurrent()) {
                TaskInfo taskInfo = dbManager.selector(TaskInfo.class).where(WhereBuilder.b("appName", "=", info.getAppName()).and("packageName", "=", info.getPackName())).findFirst();

                if (taskInfo != null) {
                    info.setChecked(false);
                }else{
                    info.setChecked(true);
                }

                taskInfos.add(info);

                if (info.isChecked()) {
                    progressSize += info.getMemorySize();
                }
            }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        Tools.CounterViewUp(counterView, progressSize, 1f);

        //正在运行的进程个数
        String count = tv_runningAppCount.getText().toString();
        tv_runningAppCount.setText(count+"("+taskInfos.size()+")");



    }

    @Override
    public void OnClearStartListener(Context context) {

    }

    @Override
    public void OnClearCompleteListener(Context context, double memory) {
            while (taskInfos.size() >0){
                adapter.removeItem(0);
            }

        tv_runningAppCount.setText("正在运行的进程"+"("+taskInfos.size()+")");
        String size = MemeryTools.convertStorage(progressSize);
        String[] str = size.split(" ");
        Tools.CounterViewDown(counterView,progressSize, 0, -1f);
    }

    /**
     * 条目点击事件显示出应用详情dialog
     * @param
     */
    private void showDialog(int position, TaskInfo info){

        dialogFragment_speedup_appinfo = DialogFragment_speedup_appinfo.newInstance(position,info);
        dialogFragment_speedup_appinfo.show(getFragmentManager(),null);

    }


    @Override
    public void OnItemClickListener(View itemView, int position) {

        TaskInfo info = taskInfos.get(position);
        //显示dialog
        showDialog(position, info);
}

    @Override
    public void OnCheckBoxClickListener(View checkbox, int position) {
        TaskInfo info = taskInfos.get(position);
        CheckBox box = (CheckBox) checkbox;
        long tempprogressSize = progressSize;
        //判断单选框是否点击
        if (box.isChecked()){

            try {
                dbManager.delete(TaskInfo.class, WhereBuilder.b("appName", "=", info.getAppName()).and("packageName", "=", info.getPackName()));

            } catch (DbException e) {
                e.printStackTrace();
            }
            info.setChecked(true);
            progressSize += info.getMemorySize();
        }else{

            try {
                dbManager.save(info);
            } catch (DbException e) {
                e.printStackTrace();
            }
            info.setChecked(false);
            progressSize -= info.getMemorySize();
        }

        Tools.CounterViewDown(counterView, tempprogressSize, progressSize, -1f);

        }




    public long getProgressSize() {
        return progressSize;
    }

    public void setProgressSize(long progressSize) {
        this.progressSize = progressSize;
    }

    public DbManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public MyRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MyRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public CounterView getCounterView() {
        return counterView;
    }

    public void setCounterView(CounterView counterView) {
        this.counterView = counterView;
    }

    public int getSize(){
        return taskInfos.size();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.onekey_clearRunningApp:
                MemeryTools mem = new MemeryTools();
                mem.setOnScanProcessListener(this);
                mem.startClean(taskInfos);
                break;
        }
    }
}





