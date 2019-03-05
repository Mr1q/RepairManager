package com.example.qjh.r.Login;

import cn.bmob.v3.BmobObject;

public class Person extends BmobObject{
        private String name;   //用户姓名
        private String Phone_Number;//电话号码

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPhone_Number() {
            return Phone_Number;
        }
        public void setPhone_Number(String Phone_Number) {
            this.Phone_Number = Phone_Number;
        }

}
