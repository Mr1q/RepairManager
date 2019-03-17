package com.example.qjh.r.Main;

import cn.bmob.v3.BmobObject;

public class Evaluate_obj extends BmobObject {
    private float score; //分数
    private String Msg;//评价信息
    private  String Id;//用户ID

    public Evaluate_obj(float score, String msg) {
        this.score = score;
        Msg = msg;
    }

    public Evaluate_obj() {
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
