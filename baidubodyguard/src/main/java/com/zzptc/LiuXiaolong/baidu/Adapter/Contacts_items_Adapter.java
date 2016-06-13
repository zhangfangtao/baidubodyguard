package com.zzptc.LiuXiaolong.baidu.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zzptc.LiuXiaolong.baidu.Model.Contacts;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.fragment.Onekey_AddContacts_Fragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by lxl97 on 2016/4/30.
 */
public class Contacts_items_Adapter extends BaseAdapter {
    private List<Contacts> list;
    private Context context;
    private LayoutInflater minflater;
    private EditText add_phone;

    private Button btn_complete;
    private FrameLayout frameLayout;
    private int mobileCount;
    private Handler handler;
    public Contacts_items_Adapter(List<Contacts> list, Context context, EditText add_phone, FrameLayout frameLayout,Button btn_complete){
        this.list = list;
        this.context = context;
        this.minflater = LayoutInflater.from(context);
        this.add_phone = add_phone;
        this.frameLayout = frameLayout;
        this.btn_complete = btn_complete;
        handler = new Handler();
        mobileCount = Onekey_AddContacts_Fragment.getMobileCount();
    }
    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = minflater.inflate(R.layout.items_add_contacts,null);

            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        Contacts con = list.get(position);

        if (con != null){
            holder.phoneNumber.setText(con.getPhoneNumber());
            if (con.getName() != null){
                holder.phoneName.setText(con.getName());
            }
        }
        holder.del_help_personbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                Onekey_AddContacts_Fragment.setMobileCount(mobileCount-list.size());
                handler.post(getMobileCount);
                if (list.size()<3){
                    add_phone.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);

                }else if (list.size()<1){
                    btn_complete.setBackgroundResource(R.color.btn_complete_unpressed);
                }

                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    private static class ViewHolder{
        @ViewInject(R.id.help_person_number)
        TextView phoneNumber;
        @ViewInject(R.id.help_person_name)
        TextView phoneName;
        @ViewInject(R.id.del_help_personbtn)
        ImageButton del_help_personbtn;
    }


    Runnable getMobileCount = new Runnable() {
        @Override
        public void run() {

//            mobileCount = Onekey_AddContacts_Fragment.getMobileCount() - list.size();
            //清空edittext的电话号码
            add_phone.setText(null);
//            Onekey_AddContacts_Fragment.setMobileCount(mobileCount);
            add_phone.setHint("请输入手机号 (还可添加"+(mobileCount-list.size())+"人)");
            if (list.size()<1){
                btn_complete.setBackgroundResource(R.color.btn_complete_unpressed);
            }
            notifyDataSetChanged();
        }
    };



}
