package com.example.ericzhang.servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*

   为了保证DownloadTask可以一直在后台运行，我们需要创建下载服务


 */



//三个参数，1、执行AsyncTask，需要传入一个字符串参数给后台任务
//2.作为进度显示单位
//3.使用整型数据作为反馈结果
public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;

    private boolean isCanceled = false;

    private boolean isPaused = false;

    private int lastProgress;



    public  DownloadTask(DownloadListener listener){
        this.listener = listener;
    }


    @Override
    protected Integer doInBackground(String... params) {

        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;

        try{
            long downloadedLength = 0;//记录已下载的文件长度

            //我们从参数中获取到下载的URL地址，并根据URL地址解析出文件名
            String downloadUrl = params[0];

            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));


            //将文件下载到sd卡中的download目录下
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            //拼了一个新的地址
            file = new File(directory + fileName);

            if (file.exists()){
                downloadedLength = file.length();
            }

            long connectLength = getContentLength(downloadUrl);
            if (connectLength == 0){

                return  TYPE_FAILED;

            }else if (connectLength == downloadedLength){

                return  TYPE_SUCCESS;

            }
            OkHttpClient client = new OkHttpClient();
            //加了个请求头，用于高速服务器想从哪个字节开始下载
            Request request = new Request.Builder()
                    .addHeader("RANGE","bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null){


                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                //跳过已下载的字节
                savedFile.seek(downloadedLength);

                byte[] b = new byte[1024];
                int total = 0;
                int len;
                //如果还能读出来1k字节，就一直往里边写
                while((len = is.read(b)) != -1){


                    //取消和暂停方法被调用了
                    if (isCanceled){

                        return  TYPE_CANCELED;

                    }else if (isPaused){

                        return TYPE_PAUSED;


                    }else{

                        total += len;
                        //off是字节数组的开始index
                        savedFile.write(b,0,len);
                        //计算已下载的百分比
                        int progress = (int) ((total + downloadedLength) * 100 /connectLength);
                        publishProgress(progress);

                    }
                }
                //
                response.body().close();
                return  TYPE_SUCCESS;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {


            try{
                if (is != null){

                    is.close();

                }

                if (savedFile != null){

                    savedFile.close();
                }

                if (isCanceled && file !=null){
                    file.delete();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return TYPE_FAILED;
    }


    //针对doInBackground的返回结果，直接相对应的结果回调
    @Override
    protected void onProgressUpdate(Integer... values) {

        int progress = values[0];
        if (progress > lastProgress){

            //给监听传值，方便更新
            listener.onProgress(progress);
            lastProgress = progress;

        }

    }

    //根据asyncTask中传入的下载状态来进行回调
    @Override
    protected void onPostExecute(Integer integer) {

        switch (integer){

            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;

        }
    }


    //暂停和取消
    //暂停
    public void pauseDownload(){

        isPaused = true;

    }

    //取消
    public void cancelDownload(){

        isCanceled = true;

    }



    private long getContentLength(String downloadUrl) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()){

            long contentLength = response.body().contentLength();
            response.close();
            return  contentLength;

        }

        return  0;


    }
}
