package com.example.melonweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.melonweather.gson.Forecast;
import com.example.melonweather.gson.Weather;
import com.example.melonweather.service.AutoUpdateService;
import com.example.melonweather.util.HttpUtil;
import com.example.melonweather.util.Utility;
import com.example.melonweather.service.AutoUpdateService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    //title
    private TextView titleCity;
    private TextView titleUpdateTime;
    //now
    private TextView degreeText;
    private TextView weatherInfoText;
    //forecast
    private LinearLayout forecastLayout;
    //aqi
    private TextView aqiText;
    private TextView pm25Text;
    //suggestion
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;

    private ImageView bingPicImg;

    //在现有视图的最外边加了刷新
    public SwipeRefreshLayout swipeRefresh;

    //在刷新的外边加个抽屉
    public DrawerLayout drawerLayout;
    private Button navButton;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //一个简单的背景图和状态栏融合在一起的方法
        if (Build.VERSION.SDK_INT >= 21){
            //拿到decorView
            View decorView = getWindow().getDecorView();
            //设置系统UI的显示
            decorView.setSystemUiVisibility(
                    //活动的布局会显示在状态栏上
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            );
            //trans parent,透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        //android:fitsSystemWindows="true",//如果为了头布局不和系统状态栏紧贴，在相关布局的父布局的里边加上fits代码
        setContentView(R.layout.activity_weather);




        //初始化各个控件
        weatherLayout = (ScrollView)findViewById(R.id.weather_layout);
        titleCity = (TextView)findViewById(R.id.title_city);
        titleUpdateTime = (TextView)findViewById(R.id.title_update_time);
        degreeText = (TextView)findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout)findViewById(R.id.forecast_layout);
        aqiText = (TextView)findViewById(R.id.api_text);
        pm25Text = (TextView)findViewById(R.id.pm25_text);
        comfortText = (TextView)findViewById(R.id.comfot_text);
        carWashText = (TextView)findViewById(R.id.car_wash_text);
        sportText = (TextView)findViewById(R.id.sport_text);
        //初始化背景图控件
        bingPicImg = (ImageView)findViewById(R.id.bing_pic_img);

        //刷新控件
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        //从计划内资源中设置颜色
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        //绑一下抽屉控件
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        //左侧的抽屉显示btn
        navButton = (Button)findViewById(R.id.nav_button);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //显示抽屉

                drawerLayout.openDrawer(GravityCompat.START);


            }
        });





        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = pref.getString("weather",null);

        //定义一个weatherid刷新的时候用
        final String weatherId;


        if (weatherString != null){

            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            //如果有缓存，拿到weatherId
            weatherId = weather.basic.weatherId;
            //显示天气信息;
            showWeatherInfo(weather);


        }else{

            //无缓存从服务器查寻天气,从上一级拿到weather_id
            weatherId = getIntent().getStringExtra("weather_id");
            //weatherLayout先显示不可见
            weatherLayout.setVisibility(View.INVISIBLE);
            //去请求数据吧
            requestWeather(weatherId);

        }

        //做刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });



        //判断背景图是不是已经存储过了
        String bingPic = pref.getString("bing_pic",null);
        //如果不为空使用glide设置背景图
        if (bingPic != null){

            Glide.with(this).load(bingPic).into(bingPicImg);
        }else{

            //去请求每日一图
            loadBingPic();

        }

    }

    /*
        根据天气id请求城市天气信息
     */

    public void requestWeather(final String weatherId){
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=f0c2b6dc7a82406facae0a3bec2874df";
        HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseText = response.body().string();
                //这个是存储
                final Weather weather = Utility.handleWeatherResponse(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //如果weather有数据了
                        if (weather != null && "ok".equals(weather.status)){
                            //缓存存一下
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);

                        }else{
                            //提示失败
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                        //页面加载完成之后要停止刷新
                        swipeRefresh.setRefreshing(false);

                    }
                });



            }

            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        //页面加载完成之后要停止刷新
                        swipeRefresh.setRefreshing(false);

                    }
                });
            }


        });

    }


    /*
        处理并展示weather实体类中的数据
     */

    public void  showWeatherInfo(Weather weather){

        //通过weather从各个model里拿数据
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;

        //设置上边几个
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);

        //移除forecastLayout上边的所有控件
        forecastLayout.removeAllViews();

        /*

            通过for循环添加forecast，注意android和ios的区别
            android只要设置好vir他就会竖向自动往下加

         */

        for (Forecast forecast : weather.forecastList){

            //联系子视图的方法
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            //从item视图拿到小控件
            TextView dateText = (TextView)view.findViewById(R.id.date_text);
            TextView infoText = (TextView)view.findViewById(R.id.info_text);
            TextView maxText = (TextView)view.findViewById(R.id.max_text);
            TextView minText = (TextView)view.findViewById(R.id.min_text);

            //给这些小控件赋值
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);

            forecastLayout.addView(view);

        }

        if (weather.aqi != null){
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);

        }

        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数" + weather.suggestion.carWash.info;
        String sport = "运动建议" + weather.suggestion.sport.info;

        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);

        weatherLayout.setVisibility(View.VISIBLE);


        if (weather != null && "ok".equals(weather.status)){

            //把服务打开，8个小时之后更新数据
            Intent intent = new Intent(this,AutoUpdateService.class);

            startService(intent);


        }else{
            //提示失败
            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
        }

    }

    /*
        加载必应每日一图

     */

    private void  loadBingPic(){


        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOKHttpRequest(requestBingPic, new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                //拿到编辑器
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);


                    }
                });



            }
            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();

            }

        });

    }







}
