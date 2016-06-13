package com.zzptc.LiuXiaolong.baidu.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.UpdateInfo;
import com.zzptc.LiuXiaolong.baidu.download.DownloadManager;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * Created by lxl97 on 2016/4/25.
 */
    @ContentView(R.layout.dialogfragment_network)
public class NetStatusFragment extends DialogFragment {
    @ViewInject(R.id.tv_info)
    private TextView tv_info;
    @ViewInject(R.id.determine)
    private Button determine;
    private DownloadManager downloadManager;
    public static NetStatusFragment newInstance(String info){
        NetStatusFragment update = new NetStatusFragment();

        Bundle bundle = new Bundle();
        bundle.putString("info",info);
        update.setArguments(bundle);
        return  update;
    }
    public static NetStatusFragment newInstance(String info, UpdateInfo updateInfo){
        NetStatusFragment update = new NetStatusFragment();
        Bundle bundle = new Bundle();
        bundle.putString("info",info);
        bundle.putSerializable("updateinfo",updateInfo);
        update.setArguments(bundle);
        return update;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = x.view().inject(this,inflater,container);

        String getinfo = getArguments().getString("info");
        tv_info.setText(getinfo);

        downloadManager = new DownloadManager();
        return v;
    }
    @Event(value = R.id.determine)
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.determine:

                UpdateInfo updateInfo = (UpdateInfo) getArguments().getSerializable("updateinfo");
                if (updateInfo != null){
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        String downloadPath = Environment.getExternalStorageDirectory() + File.separator +"download/baidusafe.apk";
                        try {
                            downloadManager.startDownload(updateInfo.getVersion_downloadUrl(),"baidusafe",downloadPath,true,true,null);

                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }

                getDialog().dismiss();
                break;
        }
    }
}
