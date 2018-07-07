package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "MyService";
    //只有开始和结束，有什么办法可以让活动和服务之间的关系更紧密一点呢？活动智慧服务去干什么，服务就干什么
    //当然可以，这就需要借助我们刚刚忽略的onBind方法了
    //完成活动和服务之间的通信

    private DownloadBinder mBinder = new DownloadBinder();



    //新建一个下载内部类, 继承自Binder，以便创建对象在bind中返回
    class DownloadBinder extends Binder {


        //开始下载
        public void startDownload(){

            Log.d(TAG, "startDownload: executed");
        }

        //查看进度
        public int getProgress(){

            Log.d(TAG, "getProgress: executed");
            return 0;

        }

    }




    public MyService() {
    }


    //Service中唯一的抽象方法，需要在子类中实现
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  mBinder;
    }

    //创建的时候调用一次
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate executed");
        //如果要使用前台服务的话在这里写入代码，不使用的话只需要把这部分注释掉
        Intent intent = new Intent(this,MainActivity.class);
        //
//        PendingIntent pi = PendingIntent.getActivities(this,0, new Intent[]{intent},0);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is content Text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();


        startForeground(1,notification);







    }

    //每次开始的时候都调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d(TAG, "onStartCommand ececuted");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.d(TAG, "onDestroy ececuted");


    }



}




