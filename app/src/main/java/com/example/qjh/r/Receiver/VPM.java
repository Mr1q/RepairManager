package com.example.qjh.r.Receiver;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.qjh.r.Adapter.SectionsPagerAdapter;
import com.example.qjh.r.Control.BaseActivity;
import com.example.qjh.r.Fragment.TotalFragment;
import com.example.qjh.r.Fragment.HomeFragment;
import com.example.qjh.r.Fragment.UserFragment;
import com.example.qjh.r.R;

import java.util.ArrayList;
import java.util.List;

public class VPM extends BaseActivity implements ViewPager.OnPageChangeListener ,BottomNavigationBar.OnTabSelectedListener{
//    private BottomNavigationBar bottomNavigationBar;
    private BottomNavigationBar bottomNavigationBar;
    private ViewPager vp;
    private static BadgeItem badgeItem;
    private  List<Fragment> arrayList;
    private long mExitTime;

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage);
        setVp();

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
        arrayList.add(new HomeFragment());
        arrayList.add(new TotalFragment());
        arrayList.add(new UserFragment());
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
            badgeItem.setText(String.valueOf(HomeFragment.getnumber()));
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
