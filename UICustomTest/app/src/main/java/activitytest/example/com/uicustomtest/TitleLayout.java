package activitytest.example.com.uicustomtest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.jar.Attributes;

//自定义控件
public class TitleLayout extends LinearLayout {


    public TitleLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        Button titleBack = (Button)findViewById(R.id.title_back);
        Button titleEdit = (Button)findViewById(R.id.title_edit);
        titleBack.setOnClickListener(new  OnClickListener(){
            @Override
            public void onClick(View v) {


                ((Activity)getContext()).finish();

            }
        });


        titleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"You clicked Edit button",Toast.LENGTH_SHORT).show();

            }
        });



    }


}
