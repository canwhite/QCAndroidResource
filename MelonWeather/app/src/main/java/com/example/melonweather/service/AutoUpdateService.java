package com.example.melonweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.melonweather.gson.Weather;
import com.example.melonweather.util.HttpUtil;
import com.example.melonweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //开始控制


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //更新天气信息
        updateWeather();
        //更新必应一图
        updateBingPic();
        //设置8个小时之后把自己启动
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 *1000;//这是8小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;//开机的时间 + 8小时差额
        Intent i= new Intent(this,AutoUpdateService.class);//作为PenddingIntent的参数，自己到自己
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        //每次都启动更新一下，前边已经更新过了，这里取消再重新设置
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);



        return super.onStartCommand(intent, flags, startId);
    }


    /*
         更新天气信息
     */

    private  void updateWeather(){

        //因为是更新，所以先拿到weather，方便后边拿到weatherid，重新请求更新
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);//这个Str包含weather的所有内容

        //只在选过之后更新，要不然不更新
        if (weatherString != null){

            //有缓存的时候直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            //得到id
            String weatherId = weather.basic.weatherId;
            String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=f0c2b6dc7a82406facae0a3bec2874df";

            //发送请求
            HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {

                //获取正常的请求结果
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String responseText = response.body().string();
                    Weather weather1 = Utility.handleWeatherResponse(responseText);

                    //判断不为空，并且状态为ok

                    if (weather1 != null && "ok".equals(weather1.status)){

                        //我们存储的是所有内容的那个字符串
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",responseText);
                        editor.apply();

                    }
                }

                //请求失败了怎么办
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    /*
        更新必应每日一图
     */

    private void updateBingPic(){

        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOKHttpRequest(requestBingPic, new Callback() {


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //
                String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();

            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }








}
