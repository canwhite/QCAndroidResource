package com.example.lbstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DistinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distination);




        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){

            actionBar.hide();

        }
        Button back = (Button)findViewById(R.id.title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });

        //获取edit

        final EditText edit = (EditText)findViewById(R.id.input_message);



        //点击发送按钮
        Button send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean is =  TextUtils.isEmpty(edit.getText().toString());
                //如果输入的内容为空
                if (is){
                    Toast.makeText(DistinationActivity.this,"输入的内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    //如果内容不为空
                    Intent intent = new Intent();
                    intent.putExtra("data_return",edit.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();

                }


            }
        });





    }
}
