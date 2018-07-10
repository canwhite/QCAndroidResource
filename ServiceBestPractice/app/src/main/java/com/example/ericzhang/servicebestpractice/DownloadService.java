package com.example.ericzhang.servicebestpractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

public class DownloadService extends Service {


    private  DownloadTask downloadTask;
    private  String downloadUrl;

    /*

        这部分是工具类中的接口实现

     */

    private  DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {

            getNotificationManager().notify(1,getNotification("Downloading...",progress));

        }



        @Override
        public void onSuccess() {

            //工具类对象清空
            downloadTask = null;
            //把前台活动给停止
            stopForeground(true);
            //创建一个下载成功的通知
            getNotificationManager().notify(1,getNotification("Download Success",-1));
            Toast.makeText(DownloadService.this,"Download Success",Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onFailed() {


            //工具类对象清空
            downloadTask = null;
            //把前台活动给停止
            stopForeground(true);
            //创建一个下载成功的通知
            getNotificationManager().notify(1,getNotification("Download Failed",-1));
            Toast.makeText(DownloadService.this,"Download Failed",Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        public void onPaused() {

            //工具类对象清空
            downloadTask = null;
            Toast.makeText(DownloadService.this,"Paused",Toast.LENGTH_SHORT)
                    .show();


        }

        @Override
        public void onCanceled() {

            //工具类对象清空
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_SHORT)
                    .show();


        }
    };




    //返回通知manager，方便调用notify和statFore
    private NotificationManager getNotificationManager(){

        return  (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

    }

    //具体通知的创建，方便给manager做参数
    private Notification getNotification(String title,int progress){

        //显示的点击跳转

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivities(this,0, new Intent[]{intent},0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if (progress > 0){

            //当progress大于或等于0才开始显示进入
            builder.setContentText(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();

    }


    /*

        这部分是要和活动建立关系

     */



    private  DownLoadBinder mBinder = new DownLoadBinder();


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  mBinder;
    }

    //先建立绑定类


    class DownLoadBinder extends Binder{

        /*

            方法在活动中被调用

         */


        //开始下载
        public void startDownload(String url){

            if (downloadTask == null){


                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                //async 对应的那三个参数在这里插入
                downloadTask.execute(downloadUrl);
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this,"Downloading...",Toast.LENGTH_SHORT).show();

            }


        }

        //暂停下载
        public void pauseDownload(){

            //从活动到此处，从此处到task，需要传递
            if (downloadTask != null){
                downloadTask.pauseDownload();

            }
        }


        //取消下载
        public void cancelDownload(){

            if(downloadTask != null){//正在下载
                downloadTask.cancelDownload();
            }else{//下载完了，或者还没启动下载直接点取消下载的情况下，清空之前下载的文件。
                if(downloadUrl != null){
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
//                    String directory = System.getenv("EXTERNAL_STORAGE");
                    File file = new File(directory+fileName);
                    System.out.println("zhixing2 path = "+directory+fileName);
                    if(file.exists()){
                        file.delete();
                        System.out.println("zhixng4");
                    }
                    getNotificationManager().cancel(1);//取消通知1
                    stopForeground(true);//结束当前前台服务
                    Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_SHORT).show();


                }
            }
        }

    }









    public DownloadService() {
    }








}
