package com.example.ericzhang.servicebestpractice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DownloadService.DownLoadBinder downLoadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //拿到binder对象
            downLoadBinder = (DownloadService.DownLoadBinder)service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startDownload = (Button)findViewById(R.id.start_download);
        Button pauseDownload = (Button)findViewById(R.id.pause_download);
        Button cancelDownload = (Button)findViewById(R.id.cancel_download);

        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);

        //启动服务
        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);

        //绑定服务
        bindService(intent,connection,BIND_AUTO_CREATE);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


        }

    }

    @Override
    public void onClick(View v) {

        if (downLoadBinder ==null){
            return;
        }

        switch (v.getId()){

            case R.id.start_download:
                String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                downLoadBinder.startDownload(url);
                break;

            case R.id.pause_download:
                downLoadBinder.pauseDownload();

                break;

            case R.id.cancel_download:
                downLoadBinder.cancelDownload();

                break;
            default:
                break;



        }









    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){


                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();


                }




                break;
            default:


        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
