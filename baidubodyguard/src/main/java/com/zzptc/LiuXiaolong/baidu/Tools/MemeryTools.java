package com.zzptc.LiuXiaolong.baidu.Tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;

import com.zzptc.LiuXiaolong.baidu.Model.AppInfo;
import com.zzptc.LiuXiaolong.baidu.Model.TaskInfo;
import com.zzptc.LiuXiaolong.baidu.R;

import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lxl97 on 2016/5/25.
 */
public class MemeryTools {
    private Context context;

    PackageManager pm;
    ApplicationInfo appinfo;
    ArrayList<TaskInfo> list;

    ActivityManager manager;
    public static ArrayList<AppInfo> appInfolist;
    public MemeryTools() {
        context = x.app().getApplicationContext();
        pm = context.getPackageManager();
        list = new ArrayList<>();
        manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
    }

    public interface OnScanProcessListener {
        void OnScanStartListener(Context context);

        void OnScanCurrentListener(Context context, int current, int total);

        void OnScanCompleteListener(Context context, List<TaskInfo> list);

        void OnClearStartListener(Context context);

        void OnClearCompleteListener(Context context, double memory);

    }

    private OnScanProcessListener mOnProcessListener;

    public void setOnScanProcessListener(OnScanProcessListener onScanProcessListener) {
        mOnProcessListener = onScanProcessListener;
    }

    public void getRunningAppInfo() {
        new GetRunningAppInfo().execute();

    }

    /**
     * 异步读取内存中应用数据
     */
    class GetRunningAppInfo extends AsyncTask<Void, Integer, List<TaskInfo>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mOnProcessListener != null) {
                mOnProcessListener.OnScanStartListener(context);
            }
        }

        @Override
        protected List<TaskInfo> doInBackground(Void... params) {
            int progress = 0;

            List<ActivityManager.RunningAppProcessInfo> run = manager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo ra : run) {
                progress++;
                publishProgress(progress, run.size());
                TaskInfo taskinfo = new TaskInfo();
                try {
                    appinfo = pm.getApplicationInfo(ra.processName, 0);
                    //应用图标
                    taskinfo.setIcon(appinfo.loadIcon(pm));
                    //应用名称
                    taskinfo.setAppName(appinfo.loadLabel(pm).toString());
                    taskinfo.setPackName(ra.processName);

                    if ((appinfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        taskinfo.setSystem(true);
                    } else {
                        taskinfo.setSystem(false);
                    }
                    if (ra.processName.equals(context.getPackageName())) {
                        taskinfo.setCurrent(true);
                    }


                } catch (PackageManager.NameNotFoundException e) {
                    if (ra.processName.contains(":")) {
                        ApplicationInfo applicationInfo = getApplication(ra.processName.split(":")[0]);
                        if (applicationInfo != null) {
                            taskinfo.setIcon(applicationInfo.loadIcon(pm));
                        } else {
                            taskinfo.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
                        }
                        taskinfo.setSystem(true);
                        taskinfo.setAppName(ra.processName);
                    }
                }
                //应用所占内存
                taskinfo.setMemorySize(manager.getProcessMemoryInfo(new int[]{ra.pid})[0].getTotalPrivateDirty() * 1024);

                list.add(taskinfo);

            }

            return list;

        }


        @Override
        protected void onPostExecute(List<TaskInfo> taskInfos) {
            super.onPostExecute(taskInfos);
            if (mOnProcessListener != null) {
                mOnProcessListener.OnScanCompleteListener(context, taskInfos);

            }


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mOnProcessListener != null) {
                mOnProcessListener.OnScanCurrentListener(context, values[0], values[1]);

            }

        }
    }

    private ApplicationInfo getApplication(String processName) {
        if (processName == null) {
            return null;
        }
        List<ApplicationInfo> allLists = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo applicationInfo : allLists) {
            if (processName.equals(applicationInfo.processName)) {
                return applicationInfo;
            }
        }
        return null;
    }


    public Boolean KillRunningAppProcess(String packageName) {
        for (ActivityManager.RunningAppProcessInfo run : manager.getRunningAppProcesses()) {
            String processName = run.processName;
            if (processName.equals(packageName)) {
                manager.killBackgroundProcesses(packageName);
                return true;
            }
        }
        return null;
    }

    public class ClearRunningApp extends AsyncTask<List<TaskInfo>, Void, Long>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mOnProcessListener != null){
                mOnProcessListener.OnClearStartListener(context);
            }
        }


        @Override
        protected Long doInBackground(List<TaskInfo>... params) {
            List<TaskInfo> taskInfos = params[0];

            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();

            long beforeMemory = getAvailMemory();
            for (ActivityManager.RunningAppProcessInfo runninginfo : runningAppProcesses){
                for (TaskInfo task : taskInfos){
                    if (task.isChecked() && task.getPackName().equals(runninginfo.processName)){
                        manager.killBackgroundProcesses(task.getPackName());
                    }
                }
            }
            long afterMemory = getAvailMemory();

            return afterMemory - beforeMemory;
        }

        @Override
        protected void onPostExecute(Long longr) {
            super.onPostExecute(longr);
            if (mOnProcessListener != null){
                System.out.println(convertStorage(longr));
                mOnProcessListener.OnClearCompleteListener(context, longr);
            }
        }

    }
    public void startClean(List<TaskInfo> list){
        new ClearRunningApp().execute(list);
    }
    public long getAvailMemory(){
       ActivityManager.MemoryInfo memory = new ActivityManager.MemoryInfo();
       manager.getMemoryInfo(memory);
       return memory.availMem;
    }

    //计算内存  以1024为单位进行计算
    public static String convertStorage(long memory){
        long kb = 1024;
        long mb = 1024 * kb;
        long gb = 1024 * mb;

        if(memory > 0){
            if(memory > gb){
                float size = (float)memory / gb;
                return String.format("%.1f GB",size);
            }else if(memory > mb){
                float size = (float)memory / mb;
                return String.format("%.1f MB",size);
            }else if(memory > kb){
                float size = (float)memory / kb;
                return String.format("%.1f KB",size);
            }else{
                return String.format("%.1f B",memory);
            }
        }else{
            return "0 B";
        }
    }



    /**
     * 获取安装的应用信息
     * @return
     */
    public ArrayList<AppInfo> getInstallAppInfo(Context context){
        ArrayList<AppInfo> list = new ArrayList<>();
        List<PackageInfo> packagelist = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i<packagelist.size(); i++){
            PackageInfo info = packagelist.get(i);
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(info.applicationInfo.loadLabel(context.getPackageManager()).toString());
            appInfo.setPackageName(info.packageName);
            appInfo.setVersionCode(info.versionCode);
            appInfo.setVersionName(info.versionName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = new Date(info.firstInstallTime *1000);
//            appInfo.setFirstInstallTime(sdf.format(dt));
            appInfo.setIcon(info.applicationInfo.loadIcon(context.getPackageManager()));
            if ((info.applicationInfo.flags & info.applicationInfo.FLAG_SYSTEM)<=0){
                list.add(appInfo);
            }
        }


        return list;
    }


    /**
     * 获取ROM大小
     */
    /**
     * 获取手机内部空间大小
     * @return
     */
    public static long getTotalInternalStorgeSize() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSize();
        long totalBlocks = mStatFs.getBlockCount();
        return totalBlocks * blockSize;
    }
    /**
     * 获取手机内部可用空间大小
     * @return
     */
    public static long getAvailableInternalStorgeSize() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSize();
        long availableBlocks = mStatFs.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static ArrayList<AppInfo> getAppInfolist() {
        return appInfolist;
    }

    public static void setAppInfolist(ArrayList<AppInfo> appInfolist) {
       MemeryTools.appInfolist = appInfolist;
    }
}

