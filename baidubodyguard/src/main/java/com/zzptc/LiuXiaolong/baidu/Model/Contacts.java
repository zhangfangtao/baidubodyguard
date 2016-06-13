package com.zzptc.LiuXiaolong.baidu.Model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by lxl97 on 2016/4/30.
 */
@Table(name = "urgentContacts")
public class Contacts implements Serializable{
    @Column(name = "ID", isId = true)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    private String Phone_calls_attribution;
    private int head_color;
    @Column(name = "sendSMS")
    private String sendSMS;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhone_calls_attribution() {
        return Phone_calls_attribution;
    }

    public void setPhone_calls_attribution(String phone_calls_attribution) {
        Phone_calls_attribution = phone_calls_attribution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHead_color() {
        return head_color;
    }

    public void setHead_color(int head_color) {
        this.head_color = head_color;
    }

    public String getSendSMS() {
        return sendSMS;
    }

    public void setSendSMS(String sendSMS) {
        this.sendSMS = sendSMS;
    }
}
