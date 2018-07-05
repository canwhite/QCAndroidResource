package activitytest.example.com.fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static activitytest.example.com.fragmenttest.R.id.left_fragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.containner, new LeftFragment()).commit();

        Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(this);



    }

    private void replaceFragment(Fragment fragment) {


        //必须先开启manager
        android.support.v4.app.FragmentManager   fragmentManager = getSupportFragmentManager();


        //开启一个事务
        android.support.v4.app.FragmentTransaction  transaction = fragmentManager.beginTransaction();


        //替换碎片，第一个参数是原来的fragment布局，第二个是你要置换的framment实例
        transaction.replace(R.id.containner, fragment);


//        //在碎片中模拟返回栈
//        transaction.addToBackStack(null);

        //对事务进行提交
        transaction.commit();



    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

//            case R.id.button:
//                replaceFragment(new AnotherRightFragment());
//                break;
            case R.id.btn1:

                replaceFragment(new LeftFragment());


                break;

            case R.id.btn2:

                replaceFragment(new AnotherRightFragment());
                break;

            default:
                break;
        }
    }
}
