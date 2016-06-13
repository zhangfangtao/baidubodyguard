package com.zzptc.LiuXiaolong.baidu.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.zzptc.LiuXiaolong.baidu.Model.Contacts;
import com.zzptc.LiuXiaolong.baidu.Model.MobileAttribution;
import com.zzptc.LiuXiaolong.baidu.Tools.ImportDB;
import com.zzptc.LiuXiaolong.baidu.Tools.Tools;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl97 on 2016/5/3.
 */
public class Read_Contacts {

    private Context context;

    ContentResolver resolver =null;
    private static final String[] phone_projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**联系人的ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;


    public Read_Contacts(Context context){
        this.context = context;

    }

    private static ArrayList<Contacts> list;

    public static ArrayList<Contacts> getList() {
        return list;
    }

    public static void setList(ArrayList<Contacts> list) {
        Read_Contacts.list = list;
    }

    //获取所有联系人信息
    public ArrayList<Contacts> getAllContacts(){
        ArrayList<Contacts> list = new ArrayList<Contacts>();
        resolver = context.getContentResolver();

        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phone_projection, null, null, null);

        if (phoneCursor != null){
            while (phoneCursor.moveToNext()){
                //手机号码
                String phonenumber = phoneCursor.getString(PHONES_NUMBER_INDEX);


                String phone = phonenumber.replace("-","").replace(" ","").replace("+86","");

                //姓名
                String contactsname = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                //id
                String contacts_id = phoneCursor.getString(PHONES_CONTACT_ID_INDEX);

                String str = getPhoneAttribute(phone);

                RandomColor randomColor = new RandomColor();
                int color = randomColor.randomColor();

                Contacts con = new Contacts();
                con.setId(contacts_id);
                con.setName(contactsname);
                con.setPhoneNumber(phone);
                con.setHead_color(color);
                con.setPhone_calls_attribution(str);
                list.add(con);
            }
            phoneCursor.close();
//
        }
        return list;
    }


    private static String getPhoneAttribute(String phone){

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("mobile.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });

        DbManager dbManager = x.getDb(daoConfig);

        if(Tools.isMobileNO(phone)){
            try {
                MobileAttribution mobileAttribute = dbManager.selector(MobileAttribution.class).where("MobileNumber","=",phone.substring(0,7)).findFirst();

                if(mobileAttribute != null){
                    return mobileAttribute.getMobileArea() + "\n" + mobileAttribute.getMobileType();
                }else{
                    return "未知号码";
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return "座机号码";
    }

}
