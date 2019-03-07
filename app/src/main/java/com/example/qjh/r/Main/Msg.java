package com.example.qjh.r.Main;

import cn.bmob.v3.BmobObject;

public class Msg extends BmobObject {
    private String Id; //主键
    private  String Message; //信息

    public Msg(String id, String message) {
        Id = id;
        Message = message;
    }
    public Msg(){};

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
