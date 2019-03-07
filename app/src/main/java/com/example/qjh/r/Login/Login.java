package com.example.qjh.r.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.annotation.Nullable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v7.widget.Toolbar;

import com.example.qjh.r.Main.Repair;
import com.example.qjh.r.R;
import com.example.qjh.r.Receiver.VPM;
import com.example.qjh.r.Text;

import Control.BaseActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends BaseActivity implements View.OnClickListener {

    //声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginButton, mLoginError, mRegister, ONLYTEST;
    private ToggleButton password_Hint; //隐藏密码
    private EditText password;//密码
    private EditText User_Number;//账号
    private Toolbar toolbar;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Bmob.initialize(this, "dff48d937894d6983e2c968c69468565");
        if (savedInstanceState != null) {
            User_Number.setText(savedInstanceState.getString("User_number"));

        }
        if (BmobUser.isLogin()) {
            Intent intent = new Intent(Login.this, VPM.class);
            startActivity(intent);
            finish();
        }
        Initial();


    }

    /*
    初始化控件
     */
    public void Initial() {
        toolbar=(Toolbar)findViewById(R.id.toolbar_title);
        toolbar.setTitle("登录界面");
        this.setSupportActionBar(toolbar);

        password_Hint = (ToggleButton) findViewById(R.id.password_Hint);
        password = (EditText) findViewById(R.id.password);
        User_Number = (EditText) findViewById(R.id.user_Number);
        mRegister = (Button) findViewById(R.id.register);  //注册按钮\

        mRegister.setOnClickListener(this);
        mLoginButton = (Button) findViewById(R.id.login);
        mLoginButton.setOnClickListener(this);
        password_Hint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password.setSelection(password.getText().toString().length()); //调整光标的位置到最后
                    password_Hint.setBackgroundResource(R.mipmap.appear); //调整图标
                } else {
                    //否则隐藏密码
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setSelection(password.getText().toString().length()); //调整光标的位置到最后
                    password_Hint.setBackgroundResource(R.mipmap.hint);  //调整图标
                }
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String data = User_Number.getText().toString().trim();
        outState.putString("User_number", data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    User_Number.setText(data.getStringExtra("username"));
                    password.setText(data.getStringExtra("password"));

                }


        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登陆activity
            case R.id.login:
//                final ProgressDialog progressDialog = new ProgressDialog(this);
//                progressDialog.setTitle("正在登录中，请稍后");
//                progressDialog.setMessage("Loading...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
                if (BmobUser.isLogin()) {
                    Intent intent = new Intent(Login.this, VPM.class);
                    startActivity(intent);
                    /*
                添加代码
                */
                } else {
                    final User user = new User();
                    //此处替换为你的用户名
                    user.setUsername(User_Number.getText().toString());
                    //此处替换为你的密码
                    user.setPassword(password.getText().toString());
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User bmobUser, BmobException e) {
                            if (e == null) {
                                User user = BmobUser.getCurrentUser(User.class);
                                Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, VPM.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "登录失败", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                break;
            //忘记密码

            //注册
            case R.id.register:
                Intent intent = new Intent(Login.this, Register.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

}








