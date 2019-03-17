package com.example.qjh.r.Receiver;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.qjh.r.Adapter.SectionsPagerAdapter;
import com.example.qjh.r.Fragment.Fragment1;
import com.example.qjh.r.Fragment.Fragment2;
import com.example.qjh.r.Fragment.Fragment4;
import com.example.qjh.r.Login.User;
import com.example.qjh.r.Main.Message_Bomb;
import com.example.qjh.r.R;

import java.util.ArrayList;
import java.util.List;

import Control.BaseActivity;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class VPM extends BaseActivity implements ViewPager.OnPageChangeListener ,BottomNavigationBar.OnTabSelectedListener{
    private BottomNavigationBar bottomNavigationBar;
    private ViewPager vp;
    private static BadgeItem badgeItem;
    private  List<Fragment> arrayList;

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage);

        setVp();
        Log.d("qjh","成功导入");

    }



    private void setVp() {
        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.bottom);
        bottomNavigationBar.setTabSelectedListener((BottomNavigationBar.OnTabSelectedListener) this);
        badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText("0").setAnimationDuration(200);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.background);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.mask, "消息").setActiveColorResource(R.color.black).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.total, "任务").setActiveColorResource(R.color.black))
                .addItem(new BottomNavigationItem(R.mipmap.me, "我的").setActiveColorResource(R.color.black))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
        arrayList=new ArrayList<Fragment>();
        arrayList.add(new Fragment2());
        arrayList.add(new Fragment1());
        arrayList.add(new Fragment4());
        vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(),arrayList));
        vp.addOnPageChangeListener(this);
        vp.setCurrentItem(1);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        bottomNavigationBar.selectTab(i);
        switch (i)
        {
            case 0: Window window =  this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(R.color.white));
                break;
            case 1:
                Window window1 =  this.getWindow();
                window1.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window1.setStatusBarColor(this.getResources().getColor(R.color.white));
                break;
            case 2:
                Window window2 =  this.getWindow();
                window2.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window2.setStatusBarColor(this.getResources().getColor(R.color.white));
                break;
        }
        if(i==1||i==2)
        {
            badgeItem.hide();
        }
        else
        {

            badgeItem.show();
            badgeItem.setText(String.valueOf(Fragment2.getnumber()));
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabSelected(int position) {
        vp.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /*
    设置消息小红点的显示
     */
    public  static void SetBadgeNum(int num)
    {
        badgeItem.setText(String.valueOf(num));
    }

}
