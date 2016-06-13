package com.zzptc.LiuXiaolong.baidu.content;

/**
 * Created by lxl97 on 2016/4/25.
 */
public class MyContents {

    public static final int REQUEST_CODE = 0;
    //有网络
    public static final int ISAVAILABLE = 1;
    //无网络
    public static final int NO_NETWORK = -1;
    //取消
    public static final int DOWN_CANCELLED = 2;
    //错误
    public static final int DOWN_ERROR = 3;
    //需要下载
    public static  final int NEED_DOWNLOAD = 4;
    //不需要下载
    public static final int NO_NEED_DOWNLOAD = 5;
    //进入其他activity请求代码
    public static final int ENTER_OTHER_ACTIVITY_CODE = 6;
    //
    public static final int EXIT_ACTIVITY = 7;
    public static final int GET_REQUEST_CONTACTS = 8;
}
