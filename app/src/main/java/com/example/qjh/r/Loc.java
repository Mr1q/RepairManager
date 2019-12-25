package com.example.qjh.r;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;


import Control.BaseActivity;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);
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
        locationClient=new LocationClient(this);
        LocationClientOption locationClientOption=new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setScanSpan(2000);
        locationClientOption.setCoorType("bd0911");
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClient.setLocOption(locationClientOption);
        baiduMap=mMapView.getMap();
        locationClient.registerLocationListener(new MyLocationLister());
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
       if(isFirstLocation) {
           LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
           MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
           baiduMap.animateMapStatus(update);
           update = MapStatusUpdateFactory.zoomTo(18f);
           baiduMap.animateMapStatus(update);
           isFirstLocation = false;
       }
            MyLocationData.Builder data=new MyLocationData.Builder();
            data.latitude(bdLocation.getLatitude());
            data.longitude(bdLocation.getLongitude());
            MyLocationData myLocationData=data.build();
            baiduMap.setMyLocationData(myLocationData);

    }

    public  class  MyLocationLister extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            thisloc=bdLocation;
            if (bdLocation == null || mMapView == null){
                return;
            }
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

}
