package com.example.qjh.r.Bean;

import cn.bmob.v3.BmobObject;

public class EvaluateObj extends BmobObject {
    private float score; //分数
    private String Msg;//评价信息
    private  String Id;//用户ID

    public EvaluateObj(float score, String msg) {
        this.score = score;
        Msg = msg;
    }

    public EvaluateObj() {
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
