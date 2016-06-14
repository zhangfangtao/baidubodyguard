package com.zzptc.LiuXiaolong.baidu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzptc.LiuXiaolong.baidu.Adapter.InstalledAdapter;
import com.zzptc.LiuXiaolong.baidu.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lxl97 on 2016/6/13.
 */
@ContentView(R.layout.fragment_installedapp)
public class Installedapp extends Fragment {
    @ViewInject(R.id.my_app_installedapp_list)
    private RecyclerView my_app_installedapp_list;
    private InstalledAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = x.view().inject(this,inflater,container);
        adapter = new InstalledAdapter(getActivity());
        my_app_installedapp_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        my_app_installedapp_list.setAdapter(adapter);
        return v;
    }
}
