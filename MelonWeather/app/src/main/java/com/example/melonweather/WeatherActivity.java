package com.example.melonweather;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.melonweather.gson.Weather;
import com.example.melonweather.util.Utility;

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

        }

    }

    /*
        根据天气id请求城市天气信息
     */

    public void requestWeather(final String weatherId){

        

    }


    /*
        处理并展示weather实体类中的数据
     */

    public void  showWeatherInfo(Weather weather){




    }





}
