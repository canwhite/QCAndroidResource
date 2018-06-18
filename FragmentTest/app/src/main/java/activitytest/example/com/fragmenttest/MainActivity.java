package activitytest.example.com.fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener( this);
//
//
//        //注意RightFragment 中的Fragment 也是继承自android.support.v4.app.Fragment
//        replaceFragment(new RightFragment());


    }

    private void replaceFragment(Fragment fragment) {


        /*
        //必须先开启manager
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        //开启一个事务
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        //替换碎片，还有一个添加碎片的方法
        transaction.replace(R.id.right_layout, fragment);
        //在碎片中模拟返回栈
        transaction.addToBackStack(null);
        //对事务进行提交
        transaction.commit();
        */

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.button:
                replaceFragment(new AnotherRightFragment());
                break;
            default:
                break;
        }
    }
}
