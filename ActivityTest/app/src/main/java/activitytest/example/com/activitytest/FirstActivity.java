package activitytest.example.com.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends BaseActivity {


    private static final String TAG = "FirstActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);// 添加layout

        //在这里打印一下，提醒创建了
        Log.d(TAG, "Task id is " + getTaskId());


        //添加按钮
        Button button1 = (Button) findViewById(R.id.button_1);
        //添加点击事件，点击事件是增加一个toast
        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           //Toast.makeText(FirstActivity.this,"You clicked Button 1",Toast.LENGTH_SHORT).show();
                                           //finish();



                                           //(1)intent的显式方法-正向传值
                                           /*
                                           String data = "Hello SecondActivity";
                                           Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                                           intent.putExtra("extra_data",data);
                                           startActivity(intent);
                                           */


                                           //(2)intent的显式方法 - 反向传值
                                           /*
                                           Intent intent = new Intent(FirstActivity.this,SecondActivity.class);

                                           //这个方法期望在活动销毁的时候，返回一个结果给上一个活动
                                           startActivityForResult(intent,1);
                                            */


                                           /*
                                           //(3)intent的隐式方法
                                           Intent intent = new Intent("activitytest.example.com.activitytest.ACTION_START");
                                           intent.addCategory("activitytest.example.com.activitytest.MY_CATEGORY");
                                           startActivity(intent);
                                           */


                                           //(4)调用系统的浏览器来打开网页
                                           /*
                                           Intent intent = new Intent(Intent.ACTION_VIEW);
                                           intent.setData(Uri.parse("http://www.baidu.com"));
                                           startActivity(intent);
                                            */

                                           //(5)调用系统拨号界面
                                           /*
                                           Intent intent = new Intent(Intent.ACTION_DIAL);
                                           intent.setData(Uri.parse("tel:10086"));
                                           startActivity(intent);
                                           */

                                           //(6)活动的启动模式：stabdard（无论是不是同一个活动都往栈顶堆）  和singleTop(同名活动在栈顶就不重新构建了)
                                           /*
                                           Intent intent = new Intent(FirstActivity.this,FirstActivity.class);
                                           startActivity(intent);
                                            */

                                           //(7)singleTop如果同名活动不在栈顶,会重新创建
                                           /*
                                           Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                                           startActivity(intent);
                                            */

                                           //(8)singleTask:每次启动该活动时，系统首先会在返回栈中检查是否存在该活动的实例
                                           //并把这个活动之上的所有活动统统出栈
                                           /*
                                           Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                                           startActivity(intent);
                                            */

                                           //(9)在将要跳转的活动中写跳转方法这样就可以让对方预设参数了

                                           SecondActivity.actionStart(FirstActivity.this,"data1","data2");


                                       }
                                   });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case 1:

                if (resultCode == RESULT_OK){

                    String returnedData = data.getStringExtra("data_return");
                    Log.d("FirstActivity", returnedData);
                }
                break;

            default:



        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return true;


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.add_item:
                Toast.makeText(this,"You clicked Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"You clicked Remove",Toast.LENGTH_SHORT).show();
                break;

            default:

        }

        return  true;

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Restart");


    }
}
