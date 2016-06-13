package com.zzptc.LiuXiaolong.baidu.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.LiuXiaolong.baidu.Adapter.Contacts_items_Adapter;
import com.zzptc.LiuXiaolong.baidu.Model.Contacts;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.Tools;
import com.zzptc.LiuXiaolong.baidu.Utils.Read_Contacts;
import com.zzptc.LiuXiaolong.baidu.activity.OneKey_for_help;
import com.zzptc.LiuXiaolong.baidu.activity.Search_contacts;
import com.zzptc.LiuXiaolong.baidu.content.MyContents;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Onekey_AddContacts_Fragment extends Fragment implements View.OnClickListener{

    private EditText help_info;
    private EditText add_phone;
    private TextView help_info_count;
    private ImageButton add_phone_picbtn;

    private Button add_phone_determine;
    private Button btn_complete;

    private ListView lv_contacts;
    private FrameLayout frameLayout;

    private Contacts_items_Adapter adapter;
    private ArrayList<Contacts> list;
    private CheckBox add_contacts_checkBox;
    private int infoCount;

    private Handler handler;

    private static int mobileCount;

    public static int getMobileCount() {
        return mobileCount;
    }

    public static void setMobileCount(int mobileCount) {
        Onekey_AddContacts_Fragment.mobileCount = mobileCount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_onekey_addcontacts,container,false);


        init(view);
        initdata();
        initListener();

        return view;
    }

    /**
     * 初始化控件等
     */
    public void init(View view){
        handler = new Handler();
        list = new ArrayList<Contacts>();


        help_info = (EditText) view.findViewById(R.id.help_info);
        add_phone = (EditText) view.findViewById(R.id.add_phone);
        help_info_count  = (TextView) view.findViewById(R.id.help_info_count);
        add_phone_picbtn = (ImageButton) view.findViewById(R.id.add_phone_picbtn);
        add_phone_determine = (Button) view.findViewById(R.id.add_phone_determine);
        lv_contacts = (ListView) view.findViewById(R.id.lv_contacts);
        frameLayout = (FrameLayout) view.findViewById(R.id.determine_btn);
        btn_complete = (Button) view.findViewById(R.id.btn_complete);
        add_contacts_checkBox = (CheckBox) view.findViewById(R.id.add_contacts_checkBox);

    }

    public void initdata(){
        if (list.size() ==0){
            btn_complete.setEnabled(false);
        }
    }


    /**
     *
     * @help_info求助信息输入数量监听     * @add_phone求助的电话号码监听
     */
    public void initListener(){

        btn_complete.setOnClickListener(this);
        add_phone_determine.setOnClickListener(this);
        add_phone_picbtn.setOnClickListener(this);
        //监听输入的求助文字数量
        help_info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //得到输入的文字长度

                help_info_count.setText(s.length()+"/40");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        add_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >0){
                    add_phone_picbtn.setVisibility(View.GONE);
                    add_phone_determine.setVisibility(View.VISIBLE);
                }else {
                    add_phone_determine.setVisibility(View.GONE);
                    add_phone_picbtn.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    Runnable getMobileCount = new Runnable() {
        @Override
        public void run() {

                setMobileCount(3 - list.size());

            //清空edittext的电话号码
            add_phone.setText(null);

            add_phone.setHint("请输入手机号 (还可添加"+getMobileCount()+"人)");
        }
    };





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyContents.REQUEST_CODE){
            switch (resultCode){
                case MyContents.GET_REQUEST_CONTACTS:

                    ArrayList<Contacts> contactses = (ArrayList<Contacts>) data.getSerializableExtra("data");

                    if (contactses != null){
                        for (Contacts c : contactses){
                            if (!isExist(c.getPhoneNumber())) {
                                list.add(c);
                            }
                        }
                    }
                    handler.post(changeContactsListView);

                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //确定添加输入的手机号码
            case R.id.add_phone_determine:
                String phoneNumber = add_phone.getText().toString();
                //查询是否是手机号码
                if (Tools.isMobileNO(phoneNumber)){
                    //是否已存在
                    if (!isExist(phoneNumber)) {
                        boolean flag = false;
                        ArrayList<Contacts> contactslist = Read_Contacts.getList();
                        for (Contacts con : contactslist) {
                            if (con.getPhoneNumber().equals(phoneNumber)) {
                                list.add(con);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            Contacts c = new Contacts();
                            c.setPhoneNumber(phoneNumber);
                            list.add(c);
                        }

                    }else{
                        Toast.makeText(getActivity(), "电话号码已存在", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }

                    handler.post(changeContactsListView);

                break;
            case R.id.add_phone_picbtn:            //跳转到搜索联系人页面

                handler.post(getMobileCount);

                Intent intent = new Intent(getActivity(),Search_contacts.class);
                System.out.println("listsize"+list.size());

                if(list.size() != 0){
                    intent.putExtra("selectoritems",list);
                }

                startActivityForResult(intent, MyContents.REQUEST_CODE);
                break;
            case R.id.btn_complete:


                if (list != null && list.size() > 0){
                    if (add_contacts_checkBox.isChecked()){
                        for (Contacts c : list){
                            sendSMS(c.getPhoneNumber(), help_info.getText().toString());
                        }
                    }
                    //将发送的信息存入数据库
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
                        //删除数据库中的数据
                        ArrayList<Contacts> dellist;
                        dellist = (ArrayList<Contacts>) manager.selector(Contacts.class).findAll();
                        if (dellist != null){
                            manager.delete(dellist);

                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    //将紧急联系人存入数据库
                    for (Contacts c : list){
                        c.setSendSMS(help_info.getText().toString());
                        try {
                            manager.save(c);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }

                    OneKey_for_help oneKey_for_help = (OneKey_for_help) getActivity();
                    oneKey_for_help.replace();
                }

                break;
        }
    }



    //添加的求助联系人列表更新
    Runnable changeContactsListView = new Runnable() {
        @Override
        public void run() {
            //显示listView
            lv_contacts.setVisibility(View.VISIBLE);
            //设置适配器

            adapter = new Contacts_items_Adapter(list,getActivity(),add_phone,frameLayout,btn_complete);
            lv_contacts.setAdapter(adapter);
            //更新可输的电话号码条数
            handler.post(getMobileCount);
            if (list.size()>0){
                btn_complete.setBackgroundResource(R.color.btn_complete_pressed);
                System.out.println(list.size());
                btn_complete.setEnabled(true);
            }

            //关闭键盘
            Tools.closeKeyboard(getActivity());
            if (list.size()==3) {
                //隐藏电话号码输入框和确定按钮
                add_phone.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
            }

        }
    };

    /**
     * 调起系统发短信功能
     * @param phoneNumber 发送短信的接收号码
     * @param message     短信内容
     */
    public Boolean sendSMS(String phoneNumber, String message) {
        // 获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
            return true;
        }
        return false;
    }
    //查询输入的电话号码是否已存在于列表中
    public Boolean isExist(String phone){
        boolean isexist = false;
        for (int i = 0; i<list.size(); i++){
            if (phone.equals(list.get(i).getPhoneNumber())){
                isexist = true;
            }
            break;

        }
        return isexist;
    }
}
