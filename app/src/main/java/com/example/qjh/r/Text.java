package com.example.qjh.r;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.qjh.r.Login.Login;

import java.util.ArrayList;
import java.util.List;

import Control.BaseActivity;
import SQlite.Text4;
import cn.bmob.v3.BmobUser;

public class Text extends BaseActivity implements View.OnClickListener,BottomNavigationBar.OnTabSelectedListener{
    private List<Fruit> fruitList=new ArrayList<Fruit>();
    private  Net net;
    private FloatingActionButton floatingActionButton;
    private Text4 text;
    private BottomNavigationBar bottomNavigationBar;
    private  BadgeItem badgeItem;
    private long mExitTime;
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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menui);
        intit();
//        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        Fruit_Adapter fruit_adapter=new Fruit_Adapter(fruitList);
//        recyclerView.setAdapter(fruit_adapter);
        net=new Net();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(net,intentFilter);


    floatingActionButton=(FloatingActionButton)findViewById(R.id.floatactionbutton1);
    floatingActionButton.setOnClickListener(this);


//        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
//        Notification notification=builder.setSmallIcon(R.drawable.ic_launcher_background)
//                .setTicker("你好")
//                .setWhen(System.currentTimeMillis())
//                .setAutoCancel(true)
//                .build();
        Notification.Builder builder=new Notification.Builder(this);
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jianshu.com/p/82e249713f1b"));
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.hint);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.reg));
        builder.setAutoCancel(true);
        builder.setContentTitle("普通通知");
        manager.notify(1,builder.build()) ;

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText("99+");
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.black);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.mask, "消息").setActiveColorResource(R.color.white).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.total, "").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.me, "我的").setActiveColorResource(R.color.white))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成

//       bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
//        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
//        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        bottomNavigationBar.setInActiveColor(R.color.white);
//        bottomNavigationBar.setActiveColor(R.color.white);
//        bottomNavigationBar.setBarBackgroundColor(R.color.white);
//        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.hint, "首页").setActiveColorResource(R.color.white))
//                .addItem(new BottomNavigationItem(R.mipmap.appear, "音乐").setActiveColorResource(R.color.white))
//                .setFirstSelectedPosition(0)
//                .initialise();
//        bottomNavigationBar.setTabSelectedListener(this);
//        badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText("99");
//        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
//        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        bottomNavigationBar.setBarBackgroundColor(R.color.black);
//        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "首页").setActiveColorResource(R.color.white))
//                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "音乐").setActiveColorResource(R.color.white))
//                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "电影").setActiveColorResource(R.color.white).setBadgeItem(badgeItem))
//                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "游戏").setActiveColorResource(R.color.white))
//                .setFirstSelectedPosition(0)
//                .initialise(); //所有的设置需在调用该方法前完成
    }


    private void intit() {
        for(int i=0;i<4;i++)
        {
            Fruit fruit1=new Fruit("水果",R.drawable.ic_launcher_foreground);
            fruitList.add(fruit1);
            Fruit fruit2=new Fruit("香蕉",R.drawable.ic_launcher_foreground);
            fruitList.add(fruit2);
            Fruit fruit3=new Fruit("栗子",R.drawable.ic_launcher_foreground);
            fruitList.add(fruit3);
            Fruit fruit4=new Fruit("核桃",R.drawable.ic_launcher_foreground);
            fruitList.add(fruit4);
            Fruit fruit5=new Fruit("可乐",R.drawable.ic_launcher_foreground);
            fruitList.add(fruit5);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.floatactionbutton1:
//                BmobUser.logOut(); //退出登录
//                Intent intent=new Intent(Text.this,Loc.class);
//                startActivity(intent);
//                Intent intent=new Intent("com.broadcasttext");
//                sendBroadcast(intent);
//                ComponentName cn = new ComponentName("com/example/qjh/r/Login", "com/example/qjh/r/Login");
//              intent.setComponent(cn);
               finish();
//                text=new Text4(this,"Bool.db",null,1);
//                text.getWritableDatabase();
        }
    }


    @Override
    public void onTabSelected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
class  Net extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isAvailable())
        {
            Toast.makeText(context,"有网络",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context,"没有网络",Toast.LENGTH_LONG).show();
        }

    }
}


