package com.zzptc.LiuXiaolong.baidu.fragment;


import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.zzptc.LiuXiaolong.baidu.Model.Contacts;
import com.zzptc.LiuXiaolong.baidu.R;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_is_open_help_info)
public class IsOpenHelpInfo extends Fragment {
    @ViewInject(R.id.iv_listanim)
    private ImageView iv_listanim;
    private AnimationDrawable animationDrawable;

    @ViewInject(R.id.urgent_list)
    private ListView urgent_list;

//    private ArrayAdapter<Contacts> adapter;
    private ArrayList<Contacts> urgentContacts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = x.view().inject(this,inflater,container);
        iv_listanim.setImageResource(R.drawable.open_onekey_help);
        animationDrawable = (AnimationDrawable) iv_listanim.getDrawable();

        init();
        initdata();
        return v;
    }

    @Override
    public void onResume() {
        animationDrawable.start();
        super.onResume();
    }

    public void init(){
        urgentContacts = new ArrayList<>();

    }

    public void initdata(){
        //读取紧急联系人数据库
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("urgent.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
        DbManager manager = x.getDb(daoConfig);
        try {
            urgentContacts = (ArrayList<Contacts>) manager.selector(Contacts.class).findAll();

            if (urgentContacts != null){
                ArrayList<String> list = new ArrayList<>();

                for (int i = 0; i< urgentContacts.size(); i++) {
                    list.add(urgentContacts.get(i).getPhoneNumber()+"   "+urgentContacts.get(i).getName());

                }
                /*adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
                urgent_list.setAdapter(adapter);*/
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
