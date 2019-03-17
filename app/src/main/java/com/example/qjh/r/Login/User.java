package com.example.qjh.r.Login;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class User extends BmobUser {
   private String Name;
    private String number;
    private String sex;
    private String riqi;
    private String address;
    private  BmobFile image;
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRiqi() {
        return riqi;
    }

    public void setRiqi(String riqi) {
        this.riqi = riqi;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
     return address;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
