package com.zzptc.LiuXiaolong.baidu.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.activity.OneKey_for_help;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_onkey)
public class Onekey_Fragment extends Fragment {

    @ViewInject(R.id.open_onekey_for_help)
    private Button open_onekey_for_help;


    private Onekey_AddContacts_Fragment addContacts_fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = x.view().inject(this,inflater,container);
        if (addContacts_fragment == null){
            addContacts_fragment = new Onekey_AddContacts_Fragment();
        }

        return v;
    }


    @Event(value = R.id.open_onekey_for_help)
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.open_onekey_for_help:

            OneKey_for_help help = (OneKey_for_help) getActivity();
                help.addfragment();

                break;
        }
    }

}
