package com.example.ericzhang.callbacktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                HttpUtils.sendHttpRequest("http://192.168.1.41/get_data.json", new QCCallBack() {
                    @Override
                    public void onFinish(String response) {
                        Log.d(TAG, "onFinish: " + response);
                    }
                    @Override
                    public void onError(Exception e) {


                    }
                });




            }
        });


    }
}
