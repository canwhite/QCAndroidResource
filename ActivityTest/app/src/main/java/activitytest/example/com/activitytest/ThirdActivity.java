package activitytest.example.com.activitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends BaseActivity {


    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Task id is" +getTaskId());

        setContentView(R.layout.third_layout);


        Button button3 = (Button)findViewById(R.id.button_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //销毁所有活动的代码
                ActivityCollector.finishAll();

                //杀掉当前进程的代码，以保证程序完全退出,只能杀掉当前进程

                android.os.Process.killProcess(android.os.Process.myPid());





            }
        });





    }
}
