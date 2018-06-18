package activitytest.example.com.localreceivertest;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private  LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localReceiver = new LocalReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("activitytest.example.com.LOCAL_RECEIVER");
        //注册本地广告坚挺，以上是两个参数
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);



        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //发送
                Intent intent = new Intent("activitytest.example.com.LOCAL_RECEIVER");
                localBroadcastManager.sendBroadcast(intent);


            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);


    }
}
