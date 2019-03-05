package com.example.qjh.r.Main;

import cn.bmob.v3.BmobObject;

public class Message_Bomb extends BmobObject {
    private String title; //标题
    private String area1; //一级区域
    private String area2;//二级区域
    private String obj_Name;//项目名字
    private String Msg;//问题描述
    private  String picture;//图片

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private  String phone;
    private String number;
    private String name;
    private String time;
    private String Id; //身份id
    public  Message_Bomb(String title,String obj_Name,String Msg,String number, String name, String phone,String time)
    {
        this.title=title;
        this.obj_Name=obj_Name;
        this.Msg=Msg;
        this.number=number;
        this.name=name;
        this.phone=phone;
        this.time=time;
    }
    public  Message_Bomb()
    {

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



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setArea1(String area1) {
        this.area1 = area1;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {

        this.picture = picture;
    }

    public void setArea2(String area2) {
        this.area2 = area2;
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

    public String getArea1() {
        return area1;
    }

    public String getArea2() {
        return area2;
    }

    public String getObj_Name() {
        return obj_Name;
    }

    public String getMsg() {
        return Msg;
    }
}
