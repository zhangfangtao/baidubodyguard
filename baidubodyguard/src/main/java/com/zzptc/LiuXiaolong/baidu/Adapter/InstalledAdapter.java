package com.zzptc.LiuXiaolong.baidu.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzptc.LiuXiaolong.baidu.Model.AppInfo;
import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.Tools.MemeryTools;

import java.util.List;

/**
 * Created by lxl97 on 2016/6/13.
 */
public class InstalledAdapter extends RecyclerView.Adapter<InstalledAdapter.MyViewHolder> {
    private List<AppInfo> list;
    private Context context;
    public InstalledAdapter(Context context){
        this.context = context;
        this.list = MemeryTools.getAppInfolist();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.items_app_manager_installed,viewGroup,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        AppInfo info = list.get(i);
        myViewHolder.img_app_manager_appIcon.setImageDrawable(info.getIcon());
        myViewHolder.tv_app_manager_item_appName.setText(info.getAppName());
        myViewHolder.tv_app_manager_installTime.setText(null);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_app_manager_appIcon;
        TextView tv_app_manager_item_appName;
        TextView tv_app_manager_installTime;
        Button btn_app_management;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_app_manager_appIcon = (ImageView) itemView.findViewById(R.id.img_app_manager_appIcon);
            tv_app_manager_item_appName = (TextView) itemView.findViewById(R.id.tv_app_manager_item_appName);
            tv_app_manager_installTime = (TextView) itemView.findViewById(R.id.tv_app_manager_installTime);
            btn_app_management = (Button) itemView.findViewById(R.id.btn_app_management);
        }
    }
}
