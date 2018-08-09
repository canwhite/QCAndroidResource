package activitytest.example.com.okhttptest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequest = (Button)findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.send_request){

            //发送请求
            //sendRequestWithHttpURLConnection();
            sendRequestWithOkHttp();
        }

    }

    /*

        OKHttp的请求，方便快捷

     */


    private void  sendRequestWithOkHttp(){

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {


                    /*
                    =======================================================
                    请求
                    =======================================================
                     */

                    /*
                    //get请求
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http:www.baidu.com")
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    Log.d(TAG, "=====" + responseData);

                    showResponse(responseData);

                    *／


                    /*
                    //post请求

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username","20100539")
                            .add("password","123456")
                            .build();

                    Request request = new Request.Builder()
                            .url("http://49.122.0.187/Hall/api/loginVerify")
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);

                    */



                    /*
                    =======================================================
                    解析
                    =======================================================
                     */



                    /*
                    //xml的Pull解析方式
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.41/get_data.xml")
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    Log.d(TAG, "=====" + responseData);

                    //xml的pull解析方式

                    parseXMLWithPull(responseData);

                    */



                    /*
                    //xml的sax解析方式
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.41/get_data.xml")
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    Log.d(TAG, "=====" + responseData);
                    parseXMLWithSAX(responseData);

                    */

                    /*
                    数据解构
                                {

	                                    "name":"qiaochao",
	                                    "age":"27",
	                                    "app":[
                                            {"id":"5","version":"5.5","name":"Clash of Clans"},
		                                    {"id":"6","version":"7.0","name":"Boom Beach"},
		                                    {"id":"7","version":"3.5","name":"Clash Royale"}
	                                    ]
                                }

                     */








                    //JsonObject 解析json数据
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.41/get_data.json")
                            .build();

                    Response response = client.newCall(request).execute();
                    //response.body()返回一个StringBuilder类型的数据
                    String responseData = response.body().string();

                    parseJSONWithJSONObject(responseData);





                    /*

                    //Gson解析json数据，我们在这里调用一下HttpUtils里边的get请求方法
                    HttpUtils.doGet("http://192.168.1.41/get_data.json", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            Log.d(TAG, "onResponse: " + responseData);
                            parseJSONWithGson(responseData);

                        }
                    });
                    */


                }catch (Exception e){

                    e.printStackTrace();

                }


            }
        }).start();



    }




    /*

        JsonObject解析json数据

     */

    private void  parseJSONWithJSONObject(String jsonData){

        try {

            /*
            //当然也可以开局一个大字典，里边含有JSONArray,字典和其他单个内容
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonObject.getJSONArray();
            jsonObject.getJSONObject();
            jsonObject.getString()
            */



            JSONObject jsonObject = new JSONObject(jsonData);

            Log.d(TAG, "out name: " + jsonObject.getString("name"));
            Log.d(TAG, "out age: " + jsonObject.getString("age"));

            JSONArray jsonArray = jsonObject.getJSONArray("app");

            for (int i = 0; i< jsonArray.length();i ++){

                JSONObject jsonObj= jsonArray.getJSONObject(i);
                String id = jsonObj.getString("id");
                String name = jsonObj.getString("name");
                String version = jsonObj.getString("version");

                Log.d(TAG, "id is" + id);
                Log.d(TAG, "name is" + name);
                Log.d(TAG, "version is" +version);
            }




            /*

            //这个是直接数组的情况

            JSONArray jsonArray = new JSONArray(jsonData);

            //通过for循环拿到数组字典中的内容

            for (int i = 0; i< jsonArray.length();i ++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");

                Log.d(TAG, "id is" + id);
                Log.d(TAG, "name is" + name);
                Log.d(TAG, "version is" +version);
            }
            */


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }





    /*
        Gson解析json数据
     */

    private  void parseJSONWithGson(String jsonData){

        Gson gson = new  Gson();
        App app = gson.fromJson(jsonData,App.class);
        Log.d(TAG, "=====: " + app.getApp().get(1).getId());
        for (int i = 0 ; i< app.getApp().size(); i++){

            Log.d(TAG, "id is" + app.getApp().get(i).getId());
            Log.d(TAG, "name is" + app.getApp().get(i).getName());
            Log.d(TAG, "version is" +app.getApp().get(i).getVersion());


        }


        /*
        //如果jsonData本身是一个数组
        Gson gson1 = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());

        for (App app1:appList){

            Log.d(TAG, "id is" +app1.getId());

        }
        */



    }











    /*

        用pull的方式解析xml

     */


    private void parseXMLWithPull(String xmlData) throws IOException {


        try {
            //解析工厂
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //通过解析工厂获取解析实例
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            //dedao当前的解析事件
            int eventType = xmlPullParser.getEventType();



            String id = "";
            String name = "";
            String version = "";


            // 如果解析工作还没有完成的话，通过next可以获取下一个解析事件
            while (eventType != XmlPullParser.END_DOCUMENT){

                //获取节点的名字
                String nodeName = xmlPullParser.getName();
                switch (eventType){

                    //开始解析某个节点
                    case XmlPullParser.START_TAG:{

                        //如果发现节点名等于 id，name或者version，就调用nextText来获取节点内具体的内容
                        if ("id".equals(nodeName)){
                            //节点中对应的value值
                            id = xmlPullParser.nextText();

                        }else if ("name".equals(nodeName)){
                            name = xmlPullParser.nextText();

                        }else if ("version".equals(nodeName)){

                            version = xmlPullParser.nextText();

                        }
                        break;

                    }
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:{

                        if ("app".equals(nodeName)){
                            Log.d(TAG, "id is " + id);
                            Log.d(TAG, "name is " + name);
                            Log.d(TAG, "version is " + version);


                        }
                        break;

                    }


                    default:
                        break;

                }

                eventType = xmlPullParser.next();

            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


    }




    /*
        xml的SAX解析方式
     */

    private void parseXMLWithSAX(String xmlData) throws ParserConfigurationException, IOException {

        try {

            //工厂单例
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();

            //将contentHandel的实例设置到XMLReader中
            xmlReader.setContentHandler(handler);
            //开始执行解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));


        } catch (SAXException e) {
            e.printStackTrace();
        }


    }

    /*

           HttpURLConnection和上边的方法做一些对比

     */

    private void sendRequestWithHttpURLConnection(){

        //开启子线程来进行网络请求，回到主线程来进行UI操作

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {

                    /*

                    //get请求
                    URL url = new URL("http://www.baidu.com");
                    //然后通过url建立链接
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");

                    */


                    //post请求

                    URL url = new URL("http://49.122.0.187/Hall/api/loginVerify");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("username=20100539&password=123456");

                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //得到返回数据
                    InputStream in = connection.getInputStream();
                    //读取返回数据流
                    reader = new BufferedReader(new InputStreamReader(in));
                    Log.d(TAG, "====== " + reader);
                    //字符流，相当于一个数组类型可以一行行的添加
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){

                        response.append(line);

                    }
                    //显示返回内容
                    showResponse(response.toString());

                    Log.d(TAG, "====== " + response.toString());


                } catch (IOException e) {

                    e.printStackTrace();

                } finally {

                    if (reader != null){

                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (connection != null){
                        connection.disconnect();

                    }



                }


            }
        }).start();
    }

    private  void showResponse(final  String response){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //在这里进行UI操作，将结果显示在界面上
                responseText.setText(response);



            }
        });
    }
}
