package com.zzptc.LiuXiaolong.baidu.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by lxl97 on 2016/5/20.
 */
@Table(name = "TaskInfo")
public class TaskInfo implements Parcelable{
    @Column(name = "_id", isId = true)
    private int processId;
    private Drawable icon;
    @Column(name = "appName")
    private String appName;
    @Column(name = "packageName")
    private String packName;
    private long memorySize;
    private boolean isSystem;
    private boolean isCurrent;
    @Column(name = "isChecked")
    private boolean isChecked = true;

    public TaskInfo(Parcel in) {
        processId = in.readInt();
        appName = in.readString();
        packName = in.readString();
        memorySize = in.readLong();
        isSystem = in.readByte() != 0;
        isCurrent = in.readByte() != 0;
        isChecked = in.readByte() != 0;
    }

    public static final Creator<TaskInfo> CREATOR = new Creator<TaskInfo>() {
        @Override
        public TaskInfo createFromParcel(Parcel in) {
            return new TaskInfo(in);
        }

        @Override
        public TaskInfo[] newArray(int size) {
            return new TaskInfo[size];
        }
    };

    public TaskInfo() {

    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(processId);
        dest.writeString(appName);
        dest.writeString(packName);
        dest.writeDouble(memorySize);
        dest.writeByte((byte) (isSystem ? 1 : 0));
        dest.writeByte((byte) (isCurrent ? 1 : 0));
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
}
