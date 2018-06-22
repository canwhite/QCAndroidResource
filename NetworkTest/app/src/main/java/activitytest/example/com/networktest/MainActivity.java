package activitytest.example.com.networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequest = (Button)findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.response_text);

        sendRequest.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.send_request){

            //发送请求
            sendRequestWithHttpURLConnection();

        }

    }

    //发送请求
    private void sendRequestWithHttpURLConnection(){

        //开启子线程来进行网络请求，回到主线程来进行UI操作

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {

                    /*

                    //get请求
                    URL url = new URL("http://www.baidu.com");
                    //然后通过url建立链接
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");

                    */

                    
                    //post请求

                    URL url = new URL("http://49.122.0.187/Hall/api/loginVerify");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("username=20100539&password=123456");

                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //得到返回数据
                    InputStream in = connection.getInputStream();
                    //读取返回数据流
                    reader = new BufferedReader(new InputStreamReader(in));
                    Log.d(TAG, "====== " + reader);
                    //字符流，相当于一个数组类型可以一行行的添加
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){

                        response.append(line);

                    }
                    //显示返回内容
                    showResponse(response.toString());

                    Log.d(TAG, "====== " + response.toString());


                } catch (IOException e) {

                    e.printStackTrace();
                    
                } finally {

                    if (reader != null){

                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (connection != null){
                        connection.disconnect();

                    }



                }


            }
        }).start();
    }

    private  void showResponse(final  String response){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //在这里进行UI操作，将结果显示在界面上
                responseText.setText(response);



            }
        });

    }

}



