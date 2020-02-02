package com.example.qjh.r.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


public class User extends BmobUser {
    private String Name;       //姓名
    private String studentNumber;    //学号
    private String sex;      //性别
    private String birthday;      //生日
    private String address;   //地址
    private BmobFile image;  //头像

    public String getName() {
        return Name;
    }

    public User setName(String name) {
        this.Name = name;
        return this;
    }


    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getRiqi() {
        return birthday;
    }

    public User setRiqi(String riqi) {
        this.birthday = riqi;
        return this;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public BmobFile getImage() {
        return image;

    }

    public User setImage(BmobFile image) {
        this.image = image;
        return this;
    }

    public String getNumber() {
        return studentNumber;
    }

    public User setNumber(String number) {
        this.studentNumber = number;
        return this;
    }

}
