package com.example.qjh.r.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.qjh.r.Bean.User;
import com.example.qjh.r.Control.BaseActivity;
import com.example.qjh.r.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity  implements View.OnClickListener{
    private EditText user_numebr; //用户账号
    private  EditText user_password; //用户密码
    private Button reg; //注册
    private ImageButton Back;//返回键
    private EditText user_password_again;// 确认密码
    private Toolbar toolbar;
    private ToggleButton Hide1;
    private ToggleButton Hide2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        user_numebr=(EditText)findViewById(R.id.account_input);
        user_password=(EditText)findViewById(R.id.password_input);
        user_password_again=(EditText)findViewById(R.id.password_again);
        Hide1=(ToggleButton)findViewById(R.id.password_Hint2);
        Hide2=(ToggleButton)findViewById(R.id.password_Hint3);

        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        reg=(Button)findViewById(R.id.reg );
        reg.setOnClickListener(this);

        Hide1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    user_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    user_password.setSelection(user_password.getText().toString().length()); //调整光标的位置到最后
                    Hide1.setBackgroundResource(R.mipmap.appear); //调整图标
                } else {
                    //否则隐藏密码
                    user_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    user_password.setSelection(user_password.getText().toString().length()); //调整光标的位置到最后
                    Hide1.setBackgroundResource(R.mipmap.hint);  //调整图标
                }
            }
        });
        Hide2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    user_password_again.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    user_password_again.setSelection(user_password_again.getText().toString().length()); //调整光标的位置到最后
                    Hide2.setBackgroundResource(R.mipmap.appear); //调整图标
                } else {
                    //否则隐藏密码
                    user_password_again.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    user_password_again.setSelection(user_password_again.getText().toString().length()); //调整光标的位置到最后
                    Hide1.setBackgroundResource(R.mipmap.hint);  //调整图标
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.reg:
                if(check())
                {
                    final BmobUser user = new BmobUser();
                    user.setUsername(user_numebr.getText().toString());
                    user.setPassword(user_password.getText().toString());
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("username",user_numebr.getText().toString().trim());
                             //   intent.putExtra("password",user_password.getText().toString().trim());
                                setResult(RESULT_OK,intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;


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
