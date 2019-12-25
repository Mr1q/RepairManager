package com.example.qjh.r;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.qjh.r.Login.Login;

import SQlite.Text4;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    View view;
    Animation alphaAnimation;
    private  Button pass_Button;//跳过按键
    private Text4 text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_main, null);
        setContentView(view);
      //  Bmob.initialize(this, "dff48d937894d6983e2c968c69468565");
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anmi);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //跳过按钮
        pass_Button=(Button)findViewById(R.id.pass_Button);
        pass_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
