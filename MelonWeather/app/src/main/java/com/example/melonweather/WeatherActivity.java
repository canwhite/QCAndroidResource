package com.example.melonweather;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melonweather.gson.Forecast;
import com.example.melonweather.gson.Weather;
import com.example.melonweather.util.HttpUtil;
import com.example.melonweather.util.Utility;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = pref.getString("weather",null);
        if (weatherString != null){

            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            //显示天气信息


        }else{

            //无缓存从服务器查寻天气,从上一级拿到weather_id
            String weatherId = getIntent().getStringExtra("weather_id");
            //weatherLayout先显示不可见
            weatherLayout.setVisibility(View.INVISIBLE);
            //去请求数据吧
            requestWeather(weatherId);

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
        for (Forecast forecast : weather.forecastList){

            //子视图添加
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            //从item视图拿到小控件
            TextView dateText = (TextView)view.findViewById(R.id.date_text);
            TextView infoText = (TextView)view.findViewById(R.id.info_text);
            



        }











    }





}
