package com.example.qjh.r.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class MessageBomb extends BmobObject {
    private String title; //标题
    private String Location;//位置
    private String obj_Name;//报修项目
    private String Msg;//问题描述

    private BmobFile picture;//图片
    private String idd;
    private String phone; //联系电话
    private String number; //学号
    private String name; //联系人姓名
    private String time; //预约时间

    public Boolean getFinish() {
        return isFinish;
    }

    public void setFinish(Boolean finish) {
        isFinish = finish;
    }

    private Boolean isFinish; //维修是否完成

    private String Self;//

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setIdd(String iddd) {
        this.idd = iddd;
    }

    public String getIdd() {
        return this.idd;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public BmobFile getPicture() {
        return picture;
    }

    public String getPicture_uri() {
        return picture.getFileUrl();
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }


    public void setObj_Name(String obj_Name) {
        this.obj_Name = obj_Name;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getTitle() {

        return title;
    }


    public String getObj_Name() {
        return obj_Name;
    }

    public String getMsg() {
        return Msg;
    }

    public MessageBomb(Boolean isFinish,String title, String obj_Name, String location, String number, String name, String time, String msg, String phone, BmobFile picture, String self) {
        this.title = title;
        this.obj_Name = obj_Name;
        this.Location = location;
        this.number = number;
        this.name = name;
        this.time = time;
        this.phone = phone;
        this.Msg = msg;
        this.picture = picture;
        this.Self = self;
        this.isFinish=isFinish;

    }

    public MessageBomb(String title, String obj_Name, String location, String number, String name, String time) {
        this.title = title;
        this.obj_Name = obj_Name;
        this.Location = location;
        this.number = number;
        this.name = name;
        this.time = time;
    }

    public MessageBomb() {
    }

    public String getSelf() {
        return Self;
    }

    public void setSelf(String self) {
        Self = self;
    }
}
