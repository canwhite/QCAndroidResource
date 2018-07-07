package com.example.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    private MyService.DownloadBinder downloadBinder;

    //一个匿名类
    private ServiceConnection connection = new ServiceConnection() {


        //接口第一个方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //拿到继承自Binder的类对象
            downloadBinder = (MyService.DownloadBinder)service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();


        }


        //接口第二个方法
        @Override
        public void onServiceDisconnected(ComponentName name) {




        }



    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = (Button)findViewById(R.id.start_service);
        Button stopService = (Button)findViewById(R.id.stop_service);
        Button bindService = (Button)findViewById(R.id.bind_service);
        Button unbindService = (Button)findViewById(R.id.unbind_service);
        Button startIntentService = (Button)findViewById(R.id.start_intent_service);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        startIntentService.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.start_service:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);//启动服务
                break;

            case R.id.stop_service:

                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);

                break;

            case R.id.bind_service:

                Intent bindIntent = new Intent(this,MyService.class);
                //第三个参数表示   在活动和服务绑定后自动创建服务，MyService中的create方法执行，onStartCommand方法不执行
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;

            case R.id.unbind_service:

                unbindService(connection);
                break;

            case R.id.start_intent_service:

                //打印主线程的id
                Log.d(TAG, "Thread id is " + Thread.currentThread().getId());
                Intent intentService = new Intent(this,MytIntentService.class);
                startService(intentService);
                break;
                
                
            default:
                break;


        }




    }
}
