package com.example.lbstest;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationCilent;
    private TextView positionText;
    private MapView mapView;
    //这个是百度地图的总控制类
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private String ditributionStr = "紫荆山";

    private Vibrator vibrator ;
    private boolean isShaking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //（1）做一些初始化操作
        SDKInitializer.initialize(this.getApplicationContext());
        mLocationCilent = new LocationClient(getApplicationContext());     //声明LocationClient类,接收一个context参数
        mLocationCilent.registerLocationListener(new MyLocationListener());//注册监听器，当获取未知信息的时候，就会回调这个定位监听器
        setContentView(R.layout.activity_main);


        //将系统自带的标题栏隐藏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){

            actionBar.hide();

        }




        positionText = (TextView)findViewById(R.id.position_text_view);
        mapView = (MapView)findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        Button stopBtn = (Button)findViewById(R.id.Stop_Shaking);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShaking){
//
//                    vibrator.cancel();
//                    isShaking = false;
                        finish();

                }

            }
        });


        Button backBtn = (Button)findViewById(R.id.title_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });




        Button pushBtn = (Button)findViewById(R.id.title_edit);
        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,DistinationActivity.class);
                startActivityForResult(intent,1);

            }
        });

        //ArrayList
        List<String> permissionList = new ArrayList<>();

        /*
            开始动态请求权限
         */
        //获取精确位置权限
        //manifest是证明的意思
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        //访问电话状态
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){

            permissionList.add(Manifest.permission.READ_PHONE_STATE);

        }
        //写入外部存储
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()){

            //将ArrayList转化成Array，需要最终个数
            String[] permissions = permissionList.toArray(new  String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);


        }else{

            requestLocation();

        }


        vibrator= (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);


        SharedPreferences pref = getSharedPreferences("dis",MODE_PRIVATE);
        //name
        String name = pref.getString("name","");
        if (!TextUtils.isEmpty(name)){
            ditributionStr = name;
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){

                    String returnedData = data.getStringExtra("data_return");
                    Log.d("MainActivity", returnedData);

                    //将这个值赋给

                    ditributionStr = returnedData;

                    SharedPreferences.Editor editor = getSharedPreferences("dis",MODE_PRIVATE).edit();
                    editor.putString("name",returnedData);
                    //提交
                    editor.apply();


                }
                break;

            default:
                break;
        }
    }

    /*
            （3）转向自己的位置
         */
    private void navigateTo(BDLocation location){



        if (isFirstLocate){
            //确定当前的位置
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);

            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;


        }

        /*
            (4)显示自己的位置

         */


        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());

        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);






    }



    private void requestLocation(){


        initLocation();
        mLocationCilent.start();

    }

    //（2）start只定位一次，如果我们快速移动，如何更新呢？

    private void initLocation(){

        LocationClientOption option = new LocationClientOption();
        //设置更新间隔
        option.setScanSpan(3000);
        //定位模式默认高精度，但是我们也可以手动选择仅设备
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        mLocationCilent.setLocOption(option);

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){

                    for (int result:grantResults){

                        if (result != PackageManager.PERMISSION_GRANTED){

                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;

                        }
                    }

                    requestLocation();

                }else{

                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
            default:

        }



    }

    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(location);


            }

            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("app为实现目标：为了自救，坐过站5次了，媳妇儿很生气~\n\n");
            currentPosition.append("目的地：").append(ditributionStr).append("：）到站之后震动提示~\n");
            currentPosition.append("停止震动请点下方按钮!!!\n\n");
            currentPosition.append("当前位置（实时更新中）:\n");
            currentPosition.append("纬度：").append(location.getLatitude()).append("\n");
            currentPosition.append("经度：").append(location.getLongitude()).append("\n");
            currentPosition.append("国家：").append(location.getCountry()).append("\n");
            currentPosition.append("省：").append(location.getProvince()).append("\n");
            currentPosition.append("市：").append(location.getCity()).append("\n");
            currentPosition.append("区：").append(location.getDistrict()).append("\n");
            currentPosition.append("街道:").append(location.getStreet()).append("\n");




            //如果字符串包含内容，就让它震动
            String str = location.getStreet();
            if (str.contains(ditributionStr)){
                //通过通知实现震动
                vibrator.vibrate(5000);
                isShaking = true;

            }

            currentPosition.append("定位方式：");

            if (location.getLocType() == BDLocation.TypeGpsLocation){

                currentPosition.append("GPS");

            }else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

                currentPosition.append("网络");
            }
            positionText.setText(currentPosition);


        }
    }



    /*
    private BDAbstractLocationListener mlocationClient = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
        }
    };
    */


    /*

        生命周期中对地图做一些操作
     */

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationCilent.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        vibrator.cancel();

    }


}
