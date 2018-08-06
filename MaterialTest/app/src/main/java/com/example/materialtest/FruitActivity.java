package com.example.materialtest;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FruitActivity extends AppCompatActivity {

    public static  final String FRUIT_NAME = "fruit_name";
    public  static  final  String FRUIT_IMAGE_ID = "fruit_image_id";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        Intent intent = getIntent();
        //通过intent存储的标题得到
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID,0);



        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView = (ImageView)findViewById(R.id.fruit_image_view);
        TextView fruitContentText = (TextView)findViewById(R.id.fruit_content_text);

        //打开左侧按钮
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){

            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.edit);

        }

        //可折叠标题栏直接设置标题
        collapsingToolbar.setTitle(fruitName);

        //glide的使用 with load into
        Glide.with(this).load(fruitImageId).into(fruitImageView);


        //关于contentText，一个函数生成内容
        String fruitContent = generateFruitContent(fruitName);
        fruitContentText.setText(fruitContent);


    }


    private String generateFruitContent(String fruitName){

        StringBuilder fruitContent = new StringBuilder();

        for (int i = 0; i< 200; i++){
            fruitContent.append(fruitName);
        }
        return  fruitContent.toString();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

}
