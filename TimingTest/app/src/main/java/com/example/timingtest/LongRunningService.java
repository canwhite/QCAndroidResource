package com.example.timingtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class LongRunningService extends Service {
    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //写个异步线程执行一些操作

        new Thread(new Runnable() {
            @Override
            public void run() {

                //在这里执行具体的操作逻辑

            }
        }).start();


        //定时执行
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 *1000;//这个是1小时的毫秒数
        long  triggerAtTime = SystemClock.elapsedRealtime() + anHour;

        //创建pendingIntent
        Intent i = new Intent(this,LongRunningService.class);//自己到自己
        PendingIntent pi = PendingIntent.getService(this,0,i,0);

        //一个小时后再次启动该自己
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);



        return super.onStartCommand(intent, flags, startId);
    }
}
