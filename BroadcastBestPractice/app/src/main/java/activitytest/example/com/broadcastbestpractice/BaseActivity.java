package activitytest.example.com.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);


    }

    //只要在栈顶的才能注册接受广播，一离开栈顶就立刻移除
    @Override
    protected void onResume() {
        super.onResume();
        // 注册筛选
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("activitytest.example.com.broadcastbestpractice.FORCE_OFFLINE");

        //接受器
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        //不在栈顶就没有意义，不能展示下边的挑战框，移除
        if (receiver != null){
            unregisterReceiver(receiver);
            receiver = null;

        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);

    }


     class ForceOfflineReceiver extends BroadcastReceiver{

         @Override
         public void onReceive(final Context context, Intent intent) {

             AlertDialog.Builder builder = new AlertDialog.Builder(context);
             builder.setTitle("Warning");
             builder.setMessage("You are forced to be offline. Please try to login again.");
             builder.setCancelable(false);
             builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                     ActivityCollector.finishALL();//销毁所有活动
                     Intent intent = new Intent(context,LoginActivity.class);
                     context.startActivity(intent);//重新启动LoginActivity

                 }
             });
             builder.show();


         }
     }




}
