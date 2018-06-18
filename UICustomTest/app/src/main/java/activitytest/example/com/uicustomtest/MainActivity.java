package activitytest.example.com.uicustomtest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将系统自带的标题栏隐藏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){

            actionBar.hide();

        }





    }
}
