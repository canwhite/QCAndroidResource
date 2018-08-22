package com.example.melonweather.gson;

import com.google.gson.annotations.SerializedName;




public class Basic {

    @SerializedName("city") public  String cityName;

    @SerializedName("id")  public  String weatherId;

    //用内部类定义一个key属性
    public Update update;

    //对应着有个字典写个内部类,内部类命名是可以随意的
    public  class  Update{

        @SerializedName("loc") public String updateTime;

    }



}
