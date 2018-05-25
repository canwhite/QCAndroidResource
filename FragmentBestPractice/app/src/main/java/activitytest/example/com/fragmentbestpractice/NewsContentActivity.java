package activitytest.example.com.fragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsContentActivity extends AppCompatActivity {

    //直接把跳转写到第二个活动中
    public  static void actionStart(Context context,String newsTitle,String newsContent){

        Intent intent = new Intent(context,NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_content",newsContent);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        //获取传入的新标题
        String newsTitle = getIntent().getStringExtra("news_title");
        //获取传入的新闻内容
        String newsContent = getIntent().getStringExtra("news_content");
        //在活动中获取碎片
        NewsContentFragment newsContentFragment = (NewsContentFragment)getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);

        //刷新
        newsContentFragment.refresh(newsTitle,newsContent);



    }
}
