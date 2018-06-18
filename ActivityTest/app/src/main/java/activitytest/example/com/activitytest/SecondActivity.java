package activitytest.example.com.activitytest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends BaseActivity {

    private static final String TAG = "SecondActivity";
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        Log.d(TAG, "Task id is" +getTaskId());
        /*
        //这个是显式传值的时候的值的显示
        Intent intent = getIntent();
        String data = ((Intent) intent).getStringExtra("extra_data");
        Log.d("SecondActivity", data);
        */

        //我们来看一下

        Button button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                //反向传值的时候使用
                Intent intent = new Intent();
                intent.putExtra("data_return","Hello FirstActivity");
                setResult(RESULT_OK,intent);
                finish();
                */

                // 验证singleTop 活动启动模式，当同名活动不在栈顶的情况下会重新创建
                /*
                Intent intent = new Intent(SecondActivity.this,FirstActivity.class);
                startActivity(intent);
                */

                //验证singleInstance 是开了不同的返回栈

                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);



            }
        });

    }

    //重写返回按钮，当back的时候也可以返回数据


    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("data_return","Hello FirstActivity");
        setResult(RESULT_OK,intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: Destory");

    }

    //其他活动往这跳转的时候，提前写好内容

    public  static  void actionStart(Context context,String data1,String data2){

        Intent intent = new Intent(context,SecondActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }



}
