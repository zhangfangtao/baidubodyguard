package com.zzptc.LiuXiaolong.baidu.Utils;

import com.zzptc.LiuXiaolong.baidu.Model.Contacts;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by lxl97 on 2016/5/13.
 */
public class Read_Db {

    public Boolean readUrgentDb(){
        boolean urgentDbIsNull = false;
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

        ArrayList<Contacts> list = new ArrayList<>();
        try {
            list = (ArrayList<Contacts>) manager.selector(Contacts.class).findAll();
            if (list != null){
                urgentDbIsNull = true;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return urgentDbIsNull;
    }

    public ArrayList<Contacts> readUrgentDbContacts(){
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

        ArrayList<Contacts> list = new ArrayList<>();
        try {
            list = (ArrayList<Contacts>) manager.selector(Contacts.class).findAll();

        } catch (DbException e) {
            e.printStackTrace();
        }

        return list;
    }

}
