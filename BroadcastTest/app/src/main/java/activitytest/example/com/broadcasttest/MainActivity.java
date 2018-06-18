package activitytest.example.com.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private  NetworkchageReceiver networkchageReceiver;

    /*
        本地广播添加第二处：声明变量
     */
    //接受器对象
    private LocalReceiver localReceiver;
    //本地广播管理
    private LocalBroadcastManager localBroadcastManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        //系统发出

        //广播过滤器,系统发出，发出时候的key就是下边的这个key
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        //注册的时候需要接收器和过滤器
        networkchageReceiver = new NetworkchageReceiver();
        registerReceiver(networkchageReceiver,intentFilter);
        */

        /*
            本地广播添加第三处,注册
         */

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        //对广播进行注册
        intentFilter = new IntentFilter();
        intentFilter.addAction("activitytest.example.com.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        //注册本地广告坚挺，以上是两个参数
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);



        //自定义标准
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent("activitytest.example.com.broadcasttest.MY_BROADCAST");
                //标准广播
                //sendBroadcast(intent);

                //有序广播:广播是有先后顺序的，而且前面的广播会把后边的广播截断，以阻止其继续传播
                //sendOrderedBroadcast(intent,null);

                /*
                    本地广播添加第四处：发送
                 */



                Intent intent = new Intent("activitytest.example.com.broadcasttest.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);//发送本地广播


            }
        });




    }


    /*

        本地广播添加第一处：内部接收器类

     */

    //自己写一个内部的接收器的类
    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();

        }
    }




    // 注销行为
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkchageReceiver);
        /*
            本地广播第五处，释放
         */
        localBroadcastManager.unregisterReceiver(localReceiver);


    }

    //接受信息
    class  NetworkchageReceiver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {


            //Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();
            //连接管家
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取网络信息
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            //对联网情况进行判断，然后弹出结果

            if (networkInfo != null && networkInfo.isAvailable()){

                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();

            }else{
                
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
