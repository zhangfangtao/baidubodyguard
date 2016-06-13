package com.zzptc.LiuXiaolong.baidu.Adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.LiuXiaolong.baidu.Model.Contacts;
import com.zzptc.LiuXiaolong.baidu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxl97 on 2016/5/3.
 */
public class Read_contacts_adapter extends BaseAdapter {
    private List<Contacts> list;
    private Context context;

    private ArrayList<Contacts> selectoritems;

    private Map<Integer,Boolean> map;
    private int count;
    private int i=0;
    private TextView title;
    private Handler handler;
    public Read_contacts_adapter(List<Contacts> list, Context context,int count,TextView title, ArrayList<Contacts> selectoritems){
        this.list = list;
        this.context = context;
        this.count = count;
        this.title = title;
        this.selectoritems = selectoritems;
        map = new HashMap<>();
        for (int i = 0; i<list.size(); i++){

            map.put(i,false);

        }

        handler = new Handler();
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
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.items_read_contacts,null);
            holder = new ViewHolder();
            holder.contacts_head = (TextView) convertView.findViewById(R.id.tv_search_contacts_head);
            holder.contacts_name = (TextView) convertView.findViewById(R.id.tv_search_contacts_name);
            holder.contacts_number = (TextView) convertView.findViewById(R.id.tv_search_contacts_number);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.contacts_checkBox);
            holder.sim_Attribution = (TextView) convertView.findViewById(R.id.sim_Attribution);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final Contacts  con = list.get(position);

            GradientDrawable drawable = (GradientDrawable) holder.contacts_head.getBackground();
            drawable.setColor(con.getHead_color());
            holder.contacts_head.setText(con.getName().substring(0,1));
            holder.contacts_number.setText(con.getPhoneNumber());
            holder.contacts_name.setText(con.getName());
            holder.sim_Attribution.setText(con.getPhone_calls_attribution());
            holder.checkBox.setChecked(map.get(position));

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (!con.getPhone_calls_attribution().equals("座机号码")) {
                    if (selectoritems !=null){
                        if (!isExist(con.getPhoneNumber())){
                            if (holder.checkBox.isChecked()) {
                                if (i < count) {
                                    map.put(position, true);
                                    handler.post(changed);
                                    i++;
                                } else {
                                    Toast.makeText(context, "已选择" + count + "个", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                map.put(position, false);
                                handler.post(changed);
                                i--;
                            }
                        }else{
                            Toast.makeText(context, "号码已选择", Toast.LENGTH_SHORT).show();
                        }
                    }else{

                            if (holder.checkBox.isChecked()) {
                                if (i < count) {
                                    map.put(position, true);
                                    handler.post(changed);
                                    i++;
                                } else {
                                    Toast.makeText(context, "已选择" + count + "个", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                map.put(position, false);
                                handler.post(changed);
                                i--;
                            }

                    }

                }else {
                    Toast.makeText(context, "请选择正确的手机号码", Toast.LENGTH_SHORT).show();
                }

                    notifyDataSetChanged();
                }

            });



        return convertView;
    }

    private static class ViewHolder{
        TextView contacts_head;
        TextView contacts_name;
        TextView contacts_number;
        TextView sim_Attribution;
        CheckBox checkBox;
    }


    Runnable changed = new Runnable() {
        @Override
        public void run() {
            title.setText("选择联系人 ("+i+")");
        }
    };


    public Map<Integer,Boolean> getMap(){
        return map;
    }

    private boolean isExist(String phone){
        boolean flag = false;

        for(Contacts c:selectoritems){
            if(c.getPhoneNumber().equals(phone)){
                flag = true;
                break;
            }
        }

        return flag;
    }
}
