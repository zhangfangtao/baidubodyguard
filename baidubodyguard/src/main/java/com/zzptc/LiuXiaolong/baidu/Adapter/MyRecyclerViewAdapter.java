package com.zzptc.LiuXiaolong.baidu.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzptc.LiuXiaolong.baidu.Model.TaskInfo;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.MemeryTools;

import java.util.ArrayList;

/**
 * Created by lxl97 on 2016/5/20.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<TaskInfo> list;
    private Context context;
    public MyRecyclerViewAdapter (ArrayList<TaskInfo> list, Context context){
        this.list = list;
        this.context = context;
    }

    public interface OnItemClickListener{
        void OnItemClickListener(View itemView, int position);
        void OnCheckBoxClickListener(View checkbox, int position);
    }

    private OnItemClickListener onClickerListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        onClickerListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_applist,viewGroup,false));


        return holder;
    }


    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {

            TaskInfo info = list.get(i);
            myViewHolder.app_name.setText(info.getAppName());
            myViewHolder.app_icon.setImageDrawable(info.getIcon());
            myViewHolder.runningAppSize.setText(MemeryTools.convertStorage(info.getMemorySize()));
            if (info.isChecked()){
                myViewHolder.applist_checkbox.setChecked(true);
            }else{
                myViewHolder.applist_checkbox.setChecked(false);
            }
        if (onClickerListener != null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickerListener.OnItemClickListener(myViewHolder.itemView, i);

                }
            });
            myViewHolder.applist_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickerListener.OnCheckBoxClickListener(myViewHolder.applist_checkbox, i);
                }
            });
        }


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView app_icon;
        TextView app_name;
        TextView runningAppSize;
        CheckBox applist_checkbox;
        public MyViewHolder(View itemView) {
            super(itemView);
            app_icon = (ImageView) itemView.findViewById(R.id.appicon);
            app_name = (TextView) itemView.findViewById(R.id.tv_appname);
            runningAppSize = (TextView) itemView.findViewById(R.id.tv_runningAppSize);
            applist_checkbox = (CheckBox) itemView.findViewById(R.id.applist_checkbox);
        }
    }



}
