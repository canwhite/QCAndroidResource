package com.example.ericzhang.callbacktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

    public static void sendHttpRequest(final String address,final  QCCallBack call){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;

                try{

                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);


                    //返回数据的数据流
                    InputStream in = connection.getInputStream();

                    //得到阅读器，把返回数据流放进去
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    //通过StringBuilder把里边的数据读出
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null){

                        response.append(line);

                    }
                    if (call != null){

                        //回调finish方法
                        call.onFinish(response.toString());


                    }



                } catch (java.io.IOException e) {

                    if (call != null){
                        call.onError(e);
                    }



                }finally {

                    //当所有都执行完的时候，关闭连接
                    if (connection != null){

                        connection.disconnect();

                    }
                }
            }
        }).start();

    }



}
