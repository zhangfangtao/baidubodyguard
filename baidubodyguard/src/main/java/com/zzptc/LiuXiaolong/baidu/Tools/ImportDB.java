package com.zzptc.LiuXiaolong.baidu.Tools;

import android.content.Context;
import org.apache.commons.io.FileUtils;
import org.xutils.DbManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by lxl97 on 2016/5/4.
 */
public class ImportDB {
    private String mobilePath = "data/data/%s/databases/";

    public String getDatabasePath(Context context){

        return String.format(mobilePath, context.getPackageName());
    }
    public String getDatabaseFile(Context context,String db){
        return getDatabasePath(context) + db;
    }
   public Boolean importMobileDb(Context context){
       boolean flag = false;
       //判断路径是否存在
       File pathdir = new File(getDatabasePath(context));
       if (!pathdir.exists()){
           pathdir.mkdir();
       }
       File dbfile = new File(getDatabaseFile(context,"mobile.db"));
       if(!dbfile.exists()){
           try {
               dbfile.createNewFile();

               URL url = context.getClass().getClassLoader().getResource("assets/mobile.db");
               FileUtils.copyURLToFile(url,dbfile);
                flag = true;
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       return flag;
   }


    }

