package com.example.ericzhang.qchelloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class QCHelloWorldActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qchello_world);
        Log.d("QCHelloWorldActivity","onCreate execute");
        Log.d("data", "onCreate: data ");

    }
}

