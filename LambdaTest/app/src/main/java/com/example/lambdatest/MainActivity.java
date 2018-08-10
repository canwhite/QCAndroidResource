package com.example.lambdatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*

            凡是只有一个待实现方法的接口，都可以使用Lambda表达式的写法

         */


        //(1)线程
        new Thread(

                //参数，然后指向实现

                () -> { }

        ).start();



        //(2)按钮
        Button btn = (Button)findViewById(R.id.btn);

        //方法参数可自动推断
        btn.setOnClickListener((v)->{});



        //(3)如果自定义单个接口去实现
        MyListener listener = (a,b) ->{

            String result = a + b;
            return  result;

        };


        //(4)自定义单个接口做参数，写一个hello方法

        hello((a,b) -> {

            String result = a +b;
            return  result;

        });
    }



    public void  hello(MyListener listener){

        String a = "hello world";
        int b = 1024;
        String result = listener.doSomething(a,b);

        Log.d(TAG, result);


    }



}
