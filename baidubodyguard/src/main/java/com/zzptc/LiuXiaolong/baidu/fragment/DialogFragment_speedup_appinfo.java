package com.zzptc.LiuXiaolong.baidu.fragment;



import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.premnirmal.textcounter.CounterView;
import com.zzptc.LiuXiaolong.baidu.Adapter.MyRecyclerViewAdapter;
import com.zzptc.LiuXiaolong.baidu.Model.TaskInfo;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.MemeryTools;
import com.zzptc.LiuXiaolong.baidu.Tools.Tools;
import com.zzptc.LiuXiaolong.baidu.activity.Mobile_speedup;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.security.acl.Group;

@ContentView(R.layout.dialogfragment_speedup_appinfo)
public class DialogFragment_speedup_appinfo extends DialogFragment implements View.OnClickListener{

    //应用名称
    @ViewInject(R.id.tv_appinfo_name)
    private TextView tv_appinfo_name;
    //内存占用
    @ViewInject(R.id.Memory_Usage)
    private TextView Memory_Usage;
    //取消按钮
    @ViewInject(R.id.close_dialog)
    private Button close_dialog;
    //清理按钮
    @ViewInject(R.id.clear_thisapp)
    private Button clear_thisapp;
    //加入白名单图片
    @ViewInject(R.id.img_add_whitelist)
    private ImageView img_add_whitelist;
    //加入白名单
    @ViewInject(R.id.tv_add_whitelist)
    private TextView tv_add_whitelist;
    //应用详情
    @ViewInject(R.id.Layout_appInfo)
    private LinearLayout appinfo;

    private TaskInfo info;

    private long temp;
    public static DialogFragment_speedup_appinfo newInstance(int position, TaskInfo info){

        DialogFragment_speedup_appinfo update = new DialogFragment_speedup_appinfo();
        Bundle bundle = new Bundle();
        bundle.putParcelable("taskinfo", info);
        bundle.putInt("position", position);
        update.setArguments(bundle);
        return update;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = x.view().inject(this,inflater,container);


        init();

        Bundle bundle = getArguments();
        if (bundle != null){
            info = bundle.getParcelable("taskinfo");
            if (info != null) {
                tv_appinfo_name.setText(info.getAppName());
                String temp = Memory_Usage.getText().toString();
                Memory_Usage.setText(temp +MemeryTools.convertStorage(info.getMemorySize()));

                if (info.isChecked()){
                    addWhiteList();
                }else{
                    removeWhiteList();
                }
            }

        }


        return v;
    }

    public void init(){
        clear_thisapp.setOnClickListener(this);
        close_dialog.setOnClickListener(this);
        tv_add_whitelist.setOnClickListener(this);
        appinfo.setOnClickListener(this);


    }
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(700, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear_thisapp:
                //清理
                int position = getArguments().getInt("position");
                TaskInfo taskInfo = getArguments().getParcelable("taskinfo");

                Mobile_speedup speedup1 = (Mobile_speedup) getActivity();
                MyRecyclerViewAdapter adapter1 = speedup1.getAdapter();


                long memorysize1 = speedup1.getProgressSize();
                CounterView counterView1 = speedup1.getCounterView();

                adapter1.removeItem(position);
                adapter1.notifyItemChanged(position);
                MemeryTools memeryTools = new MemeryTools();
                memeryTools.KillRunningAppProcess(taskInfo.getPackName());
                long temp1 = memorysize1;
                if (taskInfo.isChecked()){

                    memorysize1 -= taskInfo.getMemorySize();
                    speedup1.setProgressSize(memorysize1);
                }
                Tools.CounterViewDown(counterView1, temp1, memorysize1, -1f);

                int size = speedup1.getSize();
                TextView textview = (TextView) speedup1.findViewById(R.id.tv_runningAppCount);
                textview.setText("正在运行的进程:("+size+")");

                dismiss();
                break;
            case R.id.close_dialog:
                dismiss();
                break;
            case R.id.tv_add_whitelist:
                Mobile_speedup speedup = (Mobile_speedup) getActivity();
                DbManager dbManager = speedup.getDbManager();
                MyRecyclerViewAdapter adapter = speedup.getAdapter();
                long memorysize = speedup.getProgressSize();
                CounterView counterView = speedup.getCounterView();

                if (info.isChecked()){
                    //保存到数据库
                    try {
                        dbManager.save(info);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    removeWhiteList();
                    temp = memorysize;
                    memorysize -= info.getMemorySize();
                    info.setChecked(false);

                }else{
                    try {
                        dbManager.delete(TaskInfo.class, WhereBuilder.b("appName", "=", info.getAppName()).and("packageName", "=", info.getPackName()));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    addWhiteList();
                    memorysize += info.getMemorySize();
                    info.setChecked(true);
                }
                Tools.CounterViewDown(counterView, temp, memorysize, 1f);
                speedup.setProgressSize(memorysize);
                adapter.notifyDataSetChanged();
                break;
            case R.id.Layout_appInfo:
                TaskInfo info = getArguments().getParcelable("taskinfo");
                if (info != null) {

                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", info.getPackName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
                break;
        }
    }
    /**
     * 加入白名单
     */
    private void addWhiteList(){
        Drawable drawable = getResources().getDrawable(R.mipmap.add_writelist);
        img_add_whitelist.setImageDrawable(drawable);
        tv_add_whitelist.setText("加入白名单");
    }

    /**
     * 移除白名单
     */
    private void removeWhiteList(){
        Drawable drawable = getResources().getDrawable(R.mipmap.removewhitelist);
        img_add_whitelist.setImageDrawable(drawable);
        tv_add_whitelist.setText("移除白名单");
    }


}
