package com.example.melonweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //如果已经有数据了，那就没必要让用户再选择了，直接跳转到WeatherActivity

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        if (pref.getString("weather",null) != null){

            Intent intent = new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();

        }




    }
}
