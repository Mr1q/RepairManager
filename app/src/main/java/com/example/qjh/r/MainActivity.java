package com.example.qjh.r;

import android.content.ComponentName;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.qjh.r.Login.Login;

import SQlite.Text4;

public class MainActivity extends AppCompatActivity {
    View view;
    Animation alphaAnimation;
    private Text4 text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_main, null);
        setContentView(view);
    //    text = new Text4(this, "Book.db", null, 1);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anmi);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(MainActivity.this, "动画结束", Toast.LENGTH_SHORT).show();
//                    view.startAnimation(alphaAnimation);
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
