package activitytest.example.com.sharedpreferencestest;

import android.content.SharedPreferences;
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



        //存数据的按钮
        Button saveData = (Button)findViewById(R.id.save_data);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //context方法

                //得到Edit对象
                //创建一个文件是xml管理的，名字和只允许本应用访问
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();

                //放数据

                editor.putString("name","Tom");
                editor.putInt("age",28);
                editor.putBoolean("married",false);

                //提交
                editor.apply();




            }
        });


        //取数据的按钮
        Button restoreData = (Button) findViewById(R.id.restore_data);
        restoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                //name
                String name = pref.getString("name","");

                //age
                int age = pref.getInt("age",0);

                //婚否
                boolean married = pref.getBoolean("married",false);


                Log.d(TAG, "name is" + name);
                Log.d(TAG, "age is" + age);
                Log.d(TAG, "married is " + married);





            }
        });











    }
}
