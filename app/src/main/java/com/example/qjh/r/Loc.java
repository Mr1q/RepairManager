package com.example.qjh.r;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.map.MapStatus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;


import com.example.qjh.r.Control.BaseActivity;

public class Loc  extends BaseActivity{
    public LocationClient locationClient;
    private TextView textView;
    private MapView mMapView = null;
    private BaiduMap baiduMap; //定义百度地图控件
    private boolean isFirstLocation=true;
    private FloatingActionButton click_loc;
    private BDLocation thisloc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        setContentView(R.layout.map);
        requestPermission();
        click_loc=(FloatingActionButton)findViewById(R.id.click_loc);
        click_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thisloc!=null)
                {
                    navigate(thisloc);
                }
            }
        });

        mMapView = (MapView) findViewById(R.id.mapview);
        locationClient=new LocationClient(getApplicationContext());
        LocationClientOption locationClientOption=new LocationClientOption();

        locationClientOption.setOpenGps(true);
      //  locationClientOption.setScanSpan(2000);
        locationClientOption.setIgnoreKillProcess(true);
        locationClientOption.setOpenAutoNotifyMode();
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setPriority(LocationClientOption.GpsFirst); //设置gps优先
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

     //   locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClient.setLocOption(locationClientOption);
        baiduMap=mMapView.getMap();
        MyLocationLister myLocationLister=new MyLocationLister();
        locationClient.registerLocationListener(myLocationLister);
        baiduMap.setMyLocationEnabled(true);
        locationClient.start();







    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        MapView.setMapCustomEnable(false);
        baiduMap.setMyLocationEnabled(false);
        mMapView = null;
    }


    private  void navigate(BDLocation bdLocation)
    {
        MyLocationData.Builder data=new MyLocationData.Builder();
        data.latitude(bdLocation.getLatitude());
        data.longitude(bdLocation.getLongitude());
        bdLocation.setRadius(50f);
        data .accuracy(bdLocation.getRadius());  //设置蓝圈范围
        MyLocationData myLocationData=data.build();



        baiduMap.setMyLocationData(myLocationData);

//        if(isFirstLocation) {
           LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
         //  MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
           //baiduMap.animateMapStatus(update);

           MapStatus.Builder builder = new MapStatus.Builder();
           builder.target(latLng).zoom(20.0f);

          // MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(18f);
           baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //   baiduMap.animateMapStatus(update);
           isFirstLocation = false;
  //     }

    }

    public  class  MyLocationLister extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            thisloc=bdLocation;
            Log.d("onReceiveLocation_class", "onReceiveLocation: "+bdLocation.getLatitude());
            if (bdLocation == null || mMapView == null){
                return;
            }
            Log.d("onReceiveLocation_class", "onReceiveLocation: "+bdLocation.getLatitude());
            Log.d("onReceiveLocation_class", "onReceiveLocation: "+bdLocation.getLongitude());
            navigate(bdLocation);
//            else
//            {
//                navigate(bdLocation);
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(bdLocation.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
//                    .longitude(bdLocation.getLongitude()).build();
//            baiduMap.setMyLocationData(locData);
        }

    }
    /**
     * 请求权限
     */
    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private void requestPermission() {

        if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {// 没有权限，申请权限。
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        BAIDU_READ_PHONE_STATE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case BAIDU_READ_PHONE_STATE://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    //  startLocaion();//开始定位
                } else {
                    //用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

}
