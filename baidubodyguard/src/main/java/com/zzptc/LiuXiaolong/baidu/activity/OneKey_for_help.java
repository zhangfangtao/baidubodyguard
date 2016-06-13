package com.zzptc.LiuXiaolong.baidu.activity;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Utils.Read_Db;
import com.zzptc.LiuXiaolong.baidu.fragment.IsOpenHelpInfo;
import com.zzptc.LiuXiaolong.baidu.fragment.Onekey_AddContacts_Fragment;
import com.zzptc.LiuXiaolong.baidu.fragment.Onekey_Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_onekey_for_help)
public class OneKey_for_help extends AppCompatActivity {
    private Onekey_Fragment onekey_fragment;

    @ViewInject(R.id.onekey_for_help_toolbar)
    private Toolbar onekey_for_help_toolbar;
    @ViewInject(R.id.question)
    private ImageView question;

    @ViewInject(R.id.onekey_for_help_setting)
    private ImageView onekey_for_help_setting;
    private Onekey_AddContacts_Fragment addContacts_fragment;
    private IsOpenHelpInfo isOpenHelpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
        initListener();
    }
    public void init(){


        setSupportActionBar(onekey_for_help_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Read_Db read_db = new Read_Db();
        System.out.println(read_db.readUrgentDb());
        if (read_db.readUrgentDb()){
            replace();
        }else {
            onekeyForHelp();
        }


    }

    //添加一键求救fragment
    public void onekeyForHelp(){
        //加载一键求救fragment***************************
        if(onekey_fragment == null){
            onekey_fragment = new Onekey_Fragment();
        }
        getFragmentManager().beginTransaction().setCustomAnimations(R.animator.alpha_in_fragment,R.animator.alpha_out_fragment,R.animator.alpha_in_fragment,R.animator.alpha_out_fragment).replace(R.id.onekey_content,onekey_fragment).addToBackStack("help").commit();
        //***********
    }
    /**
     * 跳转到添加求救联系人fragment
     */
    public void addfragment(){
        if (addContacts_fragment == null){
            addContacts_fragment = new Onekey_AddContacts_Fragment();
        }
        getFragmentManager().beginTransaction().setCustomAnimations(R.animator.alpha_in_fragment,R.animator.alpha_out_fragment,R.animator.alpha_in_fragment,R.animator.alpha_out_fragment).add(R.id.onekey_content,addContacts_fragment).addToBackStack("help").commit();
        question.setVisibility(View.GONE);
    }

    public void replace(){
        question.setVisibility(View.GONE);
        onekey_for_help_setting.setVisibility(View.VISIBLE);
        getFragmentManager().popBackStackImmediate("help", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (isOpenHelpInfo == null){
            isOpenHelpInfo = new IsOpenHelpInfo();
        }
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.alpha_in_fragment,R.animator.alpha_out_fragment,R.animator.alpha_in_fragment,R.animator.alpha_out_fragment)
                .replace(R.id.onekey_content,isOpenHelpInfo).commit();

    }

    /**
     * 初始化监听
     */
    public void initListener(){
        onekey_for_help_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);

            }
        });
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() >1) {
            getFragmentManager().popBackStack();
            question.setVisibility(View.VISIBLE);
        }else{
            finish();
            overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
        }

    }


}
