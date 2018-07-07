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
            String downloadUrl = params[0];

            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));

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

                while((len = is.read(b)) != -1){

                    if (isCanceled){

                        return  TYPE_CANCELED;

                    }else if (isPaused){

                        return TYPE_PAUSED;
                    }else{

                        total += len;

                        savedFile.write(b,0,len);
                        //计算已下载的百分比
                        int progress = (int) ((total + downloadedLength) * 100 /connectLength);
                        publishProgress(progress);

                    }
                }
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
