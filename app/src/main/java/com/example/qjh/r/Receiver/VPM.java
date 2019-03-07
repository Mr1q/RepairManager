package com.example.qjh.r.Receiver;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.qjh.r.Adapter.MyPagerAdapter;
import com.example.qjh.r.Adapter.SectionsPagerAdapter;
import com.example.qjh.r.Fragment.Fragment1;
import com.example.qjh.r.Fragment.Fragment2;
import com.example.qjh.r.Fragment.Fragment3;
import com.example.qjh.r.Main.Repair;
import com.example.qjh.r.R;

import java.util.ArrayList;
import java.util.List;

import Control.BaseActivity;

public class VPM extends BaseActivity implements ViewPager.OnPageChangeListener ,BottomNavigationBar.OnTabSelectedListener{
    private BottomNavigationBar bottomNavigationBar;
    private ViewPager vp;
    private  BadgeItem badgeItem;
    private  List<Fragment> arrayList;
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
        badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText(String.valueOf(Fragment2.getnumber()));
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.blue);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.mask, "消息").setActiveColorResource(R.color.white).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.total, "任务").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.me, "我的").setActiveColorResource(R.color.white))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成

        arrayList=new ArrayList<Fragment>();
        arrayList.add(new Fragment2());
        arrayList.add(new Fragment1());
        arrayList.add(new Fragment3());
        vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(),arrayList));
        vp.addOnPageChangeListener(this);
        vp.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        bottomNavigationBar.selectTab(i);
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
}
