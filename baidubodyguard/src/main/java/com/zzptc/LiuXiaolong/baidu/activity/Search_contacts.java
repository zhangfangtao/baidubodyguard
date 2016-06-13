package com.zzptc.LiuXiaolong.baidu.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.zzptc.LiuXiaolong.baidu.Adapter.Read_contacts_adapter;
import com.zzptc.LiuXiaolong.baidu.Model.Contacts;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Utils.Read_Contacts;
import com.zzptc.LiuXiaolong.baidu.content.MyContents;
import com.zzptc.LiuXiaolong.baidu.fragment.Onekey_AddContacts_Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_search_contacts)
public class Search_contacts extends AppCompatActivity {
    @ViewInject(R.id.search_contacts_list)
    private ListView search_contacts_list;

    @ViewInject(R.id.et_search)
    private EditText et_search;

    @ViewInject(R.id.search_title)
    private TextView title;

    @ViewInject(R.id.search_determine)
    private TextView search_determine;

    @ViewInject(R.id.search_contacts_toolbar)
    private Toolbar search_contacts_toolbar;

    private Read_contacts_adapter adapter;


    private Handler handler;
    private List<Contacts> contacts;

    private List<Contacts> contactses;
    private ArrayList<Contacts> selectoritems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initdata();
        initListener();



    }

    /**
     * 初始化适配器等数据
     */
    public void initdata(){
        setSupportActionBar(search_contacts_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        contactses = new ArrayList<>();
        contacts = Read_Contacts.getList();
        contactses.addAll(contacts);
        Intent intent = getIntent();
         selectoritems = (ArrayList<Contacts>) intent.getSerializableExtra("selectoritems");
        //联系人数据


        handler = new Handler();
/*
.

        adapter = new Read_contacts_adapter(contactses,this, Onekey_AddContacts_Fragment.getMobileCount(),title, selectoritems);
        search_contacts_list.setAdapter(adapter);*/

    }

    /**
     * 监听
     */
    public void initListener(){
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.post(echangedListview);
            }
        });

        //toolbar返回键
        search_contacts_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);


            }
        });
    }

    Runnable echangedListview = new Runnable() {
        @Override
        public void run() {
            String data = et_search.getText().toString();
            contactses.clear();

            getSearchContacts(contactses,data);

            adapter.notifyDataSetChanged();
        }
    };
    /**
     * 根据输入的电话号码或者姓名查询联系人
     * @param list
     * @param data
     */
    public void getSearchContacts(List<Contacts> list,String data){

        int length = contacts.size();
        for (int i = 0; i<length; i++){
            if (contacts.get(i).getName().contains(data) || contacts.get(i).getPhoneNumber().contains(data)){
                Contacts con = new Contacts();

                con.setId(contacts.get(i).getId());
                con.setPhone_calls_attribution(contacts.get(i).getPhone_calls_attribution());
                con.setHead_color(contacts.get(i).getHead_color());
                con.setName(contacts.get(i).getName());
                con.setPhoneNumber(contacts.get(i).getPhoneNumber());
                list.add(con);
            }

        }
    }

    /**
     * 点击事件监听
     * @param v
     */
    @Event(value = R.id.search_determine)
    private void getEvent(View v){
        switch (v.getId()){
            case R.id.search_determine:

                ArrayList<Contacts> list = new ArrayList<>();
                Map<Integer,Boolean> map = adapter.getMap();
                for (Integer key : map.keySet()){
                    boolean flag = map.get(key);
                    if (flag){
                        Contacts con = contacts.get(key);
                        list.add(con);

                    }


                }

                    Intent intent = new Intent();
                    intent.putExtra("data", list);
                    setResult(MyContents.GET_REQUEST_CONTACTS, intent);
                    finish();

                break;
        }
    }
}
