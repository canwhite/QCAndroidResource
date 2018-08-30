package com.example.melonweather.util;

import android.text.TextUtils;

import com.example.melonweather.db.City;
import com.example.melonweather.db.County;
import com.example.melonweather.db.Province;
import com.example.melonweather.gson.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*

    这个类对请求结果进行处理


 */


public class Utility {


    /*

        解析和处理服务器返回的省级数据

     */

    public static boolean handleProvinceResponse(String response){


        if (!TextUtils.isEmpty(response)){


            try{

                //里边的内容是数组包着的
                JSONArray allProvinces = new JSONArray(response);

                for (int i = 0; i < allProvinces.length() ; i++){

                    //得到内部的字典
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    //new 一个db省份类
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();

                }

                return  true;


            }catch (JSONException e){
                e.printStackTrace();
            }

        }



        return  false;

    }


    /*

        解析和处理服务器返回的市级数据

     */


    public static boolean handleCityResponse(String response,int provinceId){


        if (!TextUtils.isEmpty(response)){



            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {

                    JSONObject cityObject = allCities.getJSONObject(i);

                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();


                }

                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return  false;

    }




    /*

        解析和处理服务器返回的县级数据

     */


    public  static  boolean handleCountyResponse(String response,int cityId){

        if (!TextUtils.isEmpty(response)){

            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {

                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.save();

                }

                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return false;
    }



    /*
        将返回Json数据解析成weather实体类

     */

    public  static Weather handleWeatherResponse(String response){


        try{

            JSONObject jsonObject = new JSONObject(response);
            //然后得到heweather数组，我们这个数组里边只有一个元素
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            //得到数组中的首元素，转化成字符串
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return  new Gson().fromJson(weatherContent,Weather.class);

        }catch (Exception e){

            e.printStackTrace();

        }
        return  null;
    }












}
