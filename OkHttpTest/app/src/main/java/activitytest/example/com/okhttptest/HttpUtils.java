package activitytest.example.com.okhttptest;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
//使用演示

(1)===================get请求演示=============================================
这里使用response.body().string()返回响应结果内容
HttpUtils.doGet("https://www.so.com/", new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.isSuccessful()){
            tv.setText(response.body().string());
        }
        //关闭防止内存泄漏
        if(response.body()!=null){
            response.body().close();
        }
    }
});

(2)==================post发送键值对演示========================================

Map<String,String> map = new HashMap<>();
map.put("username","Hensen");
map.put("password","123456");

HttpUtils.doPost("http://192.168.31.109:8080/test.php", map, new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
});

(3)=================post发送json数据演示=======================================
String json = "{\"name\":\"Hensen\"}";

HttpUtils.doPost("http://192.168.0.8:8080/test.php", json, new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
});

(4)=====================上传文件演示=============================================

//参数1：url
//参数2：保存文件的路径名
//参数3：保存文件的文件名
//参数4：请求回调

HttpUtils.doFile("http://192.168.0.8:8080/WD/admin.php?a=ajaxuploadimage&&c=Image",
                "/data/data/com.handsome.app4/logoa.jpg", "logoa.jpg", new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
});

(5)=========================下载文件演示==========================================

//参数1：url
//参数2：保存文件的路径名
//参数3：保存文件的文件名

HttpUtils.downFile("http://shouji.360tpcdn.com/160804/a05b75b7779f7a4afae83601c195ed2b" +
                "/com.qihoo.haosou_708.apk"
                ,"/data/data/com.handsome.app4/","aqy.apk");

 */


public class HttpUtils {


    private static OkHttpClient client = null;

    private HttpUtils() {}

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (HttpUtils.class) {
                if (client == null)
                    client = new OkHttpClient();
            }
        }
        return client;
    }

    /**
     * Get请求
     *
     * @param url
     * @param callback
     */
    public static void doGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        //这边赋值，外边实现
        Call call = getInstance().newCall(request);
        //开子线程，并将最终结果显示在callBack里
        call.enqueue(callback);
    }





    /**
     * Post请求发送键值对数据
     *
     * @param url
     * @param mapParams
     * @param callback
     */
    public static void doPost(String url, Map<String, String> mapParams, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * Post请求发送JSON数据
     *
     * @param url
     * @param jsonParams
     * @param callback
     */
    public static void doPost(String url, String jsonParams, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param pathName
     * @param fileName
     * @param callback
     */
    public static void doFile(String url, String pathName, String fileName, Callback callback) {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(), fileName,
                        RequestBody.create(MEDIA_TYPE, new File(pathName)));
        //发出请求参数
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);

    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */

    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 下载文件
     * @param url
     * @param fileDir
     * @param fileName
     */
    public static void downFile(String url, final String fileDir, final String fileName) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(fileDir, fileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }






}
