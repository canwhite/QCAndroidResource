package com.baidu.location.demo;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.baidulocationdemo.R;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;


/**
 * 适配Android 8.0限制后台定位的功能，新增允许后台定位的接口，即开启一个前台定位服务
 */
public class ForegroundActivity extends Activity {
    private LocationClient mClient;
    private MyLocationListener myLocationListener = new MyLocationListener();

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Button mForegroundBtn;

    private NotificationUtils mNotificationUtils;
    private Notification notification;

    private boolean isFirstLoc = true;
    private boolean isEnableLocInForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foreground);

        initViews();

        // 定位初始化
        mClient = new LocationClient(this);
        LocationClientOption mOption = new LocationClientOption();
        mOption.setScanSpan(5000);
        mOption.setCoorType("bd09ll");
        mOption.setIsNeedAddress(true);
        mOption.setOpenGps(true);
        mClient.setLocOption(mOption);
        mClient.registerLocationListener(myLocationListener);
        mClient.start();

        //设置后台定位
        //android8.0及以上使用NotificationUtils
        if (Build.VERSION.SDK_INT >= 26) {
            mNotificationUtils = new NotificationUtils(this);
            Notification.Builder builder2 = mNotificationUtils.getAndroidChannelNotification
                    ("适配android 8限制后台定位功能", "正在后台定位");
            notification = builder2.build();
        } else {
            //获取一个Notification构造器
            Notification.Builder builder = new Notification.Builder(ForegroundActivity.this);
            Intent nfIntent = new Intent(ForegroundActivity.this, ForegroundActivity.class);

            builder.setContentIntent(PendingIntent.
                    getActivity(ForegroundActivity.this, 0, nfIntent, 0)) // 设置PendingIntent
                    .setContentTitle("适配android 8限制后台定位功能") // 设置下拉列表里的标题
                    .setSmallIcon(R.drawable.ic_launcher) // 设置状态栏内的小图标
                    .setContentText("正在后台定位") // 设置上下文内容
                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

            notification = builder.build(); // 获取构建好的Notification
        }
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
        mClient.disableLocInForeground(true);
        mClient.unRegisterLocationListener(myLocationListener);
        mClient.stop();
    }


    private void initViews(){
        mForegroundBtn = (Button) findViewById(R.id.bt_foreground);
        mForegroundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEnableLocInForeground){
                    //关闭后台定位（true：通知栏消失；false：通知栏可手动划除）
                    mClient.disableLocInForeground(true);
                    isEnableLocInForeground = false;
                    mForegroundBtn.setText(R.string.startforeground);
                } else {
                    //开启后台定位
                    mClient.enableLocInForeground(1, notification);
                    isEnableLocInForeground = true;
                    mForegroundBtn.setText(R.string.stopforeground);
                }
            }
        });
        mMapView = (MapView) findViewById(R.id.mv_foreground);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
    }


    class  MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder().accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            //地图SDK处理
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            OverlayOptions dotOption = new DotOptions().center(point).color(0xAAFF0000);
            mBaiduMap.addOverlay(dotOption);
        }
    }

}
