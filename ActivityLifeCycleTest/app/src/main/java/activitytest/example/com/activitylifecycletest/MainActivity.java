package activitytest.example.com.activitylifecycletest;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //在活动被回收，又重新创建的过程中，重新拿到buddle中的数据，赋值给需要赋值的控件
        if (savedInstanceState != null){

            String tempData = savedInstanceState.getString("data_key");
            Log.d(TAG,tempData);


        }




        Log.d(TAG, "onCreate: Create");
        
        //在这里完成数据的初始化
        Button startNormalActivity = (Button) findViewById(R.id.start_normal_activity);

        Button startDialogActivity = (Button) findViewById(R.id.start_dialog_activity);

        startNormalActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent);

            }
        });

        startDialogActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,DialogActivity.class);
                startActivity(intent);


            }
        });


    }

    @Override
    protected void onStart() {
        
        super.onStart();
        Log.d(TAG, "onStart: Start");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Resume");
        
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Parse");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Stop");
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Destory");
        
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Restart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "Something you just typed";
        outState.putString("data_key",tempData);

    }
}
