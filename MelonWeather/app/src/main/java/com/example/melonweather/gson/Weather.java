package com.example.melonweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    public  String status;


    //针对字典

    public  Basic basic;

    public  AQI aqi;

    public Now now;

    public Suggestion suggestion;

    //针对数组字典
    @SerializedName("daily_forecast") public List<Forecast> forecastList;



}
