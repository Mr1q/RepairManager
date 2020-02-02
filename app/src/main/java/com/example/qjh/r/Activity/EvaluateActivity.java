package com.example.qjh.r.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.qjh.r.Bean.EvaluateObj;
import com.example.qjh.r.Bean.User;
import com.example.qjh.r.Control.BaseActivity;
import com.example.qjh.r.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class EvaluateActivity extends BaseActivity implements View.OnClickListener {
    private  RatingBar star;
    private  static float score;
    private Toolbar toolbar;
    private Button Hand_in_value;
    private EditText Text_msg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.designer);
        Window window =  this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        intit();
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

    private void intit() {
        Text_msg=(EditText)findViewById(R.id.Text_msg);
        Hand_in_value=(Button)findViewById(R.id.Hand_in_value);
        Hand_in_value.setOnClickListener(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar_design);
        toolbar.setTitle("评价");
        toolbar.setBackgroundColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        star=(RatingBar)findViewById(R.id.stars);
        star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(EvaluateActivity.this,"你选择了"+String.valueOf(rating)+"分",Toast.LENGTH_SHORT).show();
                score=rating;

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Hand_in_value:
                EvaluateObj evaluate_obj=new EvaluateObj();
                evaluate_obj.setScore(score);
                evaluate_obj.setMsg(Text_msg.getText().toString().trim());
                evaluate_obj.setId(BmobUser.getCurrentUser(User.class).getObjectId());
                evaluate_obj.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null)
                        {
                            Toast.makeText(EvaluateActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

                break;

        }
    }
}
