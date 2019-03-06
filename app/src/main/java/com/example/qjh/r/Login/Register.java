package com.example.qjh.r.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.qjh.r.R;

import javax.xml.transform.Result;

import Control.BaseActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register extends BaseActivity  implements View.OnClickListener{
    private EditText user_numebr; //用户账号
    private  EditText user_password; //用户密码
    private Button reg; //注册
    private ImageButton Back;//返回键
    private EditText user_password_again;// 确认密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        user_numebr=(EditText)findViewById(R.id.account_input);
//        user_numebr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    user_numebr.setText("");
//                } else {
//                    if(user_numebr.getText().equals(""))
//                    {
//                        user_numebr.setText("请输入注册的账号");
//                    }
//                }
//            }
//        });
        user_password=(EditText)findViewById(R.id.password_input);
//        user_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    user_password.setText("");
//
//                } else {
//                    if(user_password.getText().equals(""))
//                    {
//                        user_password.setText("请输入6-12位字符");
//                    }
//                }
//            }
//        });
        user_password_again=(EditText)findViewById(R.id.password_again);
//        user_password_again.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    user_password.setText("");
//                } else {
//                    if(user_password.getText().equals(""))
//                    {
//                        user_password.setText("请输入6-12位字符");
//                    }
//                }
//            }
//        });

        reg=(Button)findViewById(R.id.reg );
        Back=(ImageButton)findViewById(R.id.back1);
        Back.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.reg:
                if(check())
                {
                    final User user = new User();
                    user.setUsername(user_numebr.getText().toString());
                    user.setPassword(user_password.getText().toString());
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Register.this,Login.class);
                                intent.putExtra("username",user_numebr.getText().toString().trim());
                                intent.putExtra("password",user_password.getText().toString().trim());
                                setResult(RESULT_OK,intent);
                                finish();
                            } else {
                                Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
            case R.id.back1:
                finish();

        }
    }

    public boolean check() {
            if(user_numebr.getText().toString().equals(""))
            {
                Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show();
            }
            else  if(user_password.length()<6)
            {
                Toast.makeText(this,"密码长度不足6位",Toast.LENGTH_SHORT).show();

            }
            else if(user_password_again.getText().toString().equals(user_password.getText().toString()))
            {
                return true;
            }
            else
            {
                Toast.makeText(this,"密码不匹配",Toast.LENGTH_SHORT).show();
            }
        return false;



    }
}
