package com.byx.zz.springviewdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liaoinstan.springview.container.BaseHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private  SpringView spingview;
    private Context context;
    List<String> list=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      context=this;
        initView();
        initData();

    }



    RecyclerView recyclerView;
    private void initView() {
         recyclerView= (RecyclerView) findViewById(R.id.recycler);
        spingview= (SpringView) findViewById(R.id.spingview);
        refresh();
        spingview.setHeader(new DefaultHeader(context));
        spingview.setFooter(new DefaultFooter(context));


    }


    private void initData() {
        //往list里面增加数据
        list.add("http://f10.baidu.com/it/u=2881303562,336932824&fm=72");
        list.add("http://f11.baidu.com/it/u=681755471,2018070071&fm=72");
        list.add("http://f10.baidu.com/it/u=960650584,863938083&fm=72");
        list.add("http://img0.imgtn.bdimg.com/it/u=783060973,4278100629&fm=27&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=3743124979,3234956668&fm=27&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=3468613159,957707785&fm=27&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=2971205354,485034289&fm=27&gp=0.jpg");
        list.add("http://f10.baidu.com/it/u=2881303562,336932824&fm=72");
        list.add("http://f11.baidu.com/it/u=681755471,2018070071&fm=72");
        list.add("http://f10.baidu.com/it/u=960650584,863938083&fm=72");
        list.add("http://img0.imgtn.bdimg.com/it/u=783060973,4278100629&fm=27&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=3743124979,3234956668&fm=27&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=3468613159,957707785&fm=27&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=2971205354,485034289&fm=27&gp=0.jpg");

        //设置管理者 new的部分可以换LinearLayoutManager 当前的是瀑布式布局 后面参数是几行的个数，和方向
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL));
        //设置适配器  参数是一个上下文，和list的集合
        RecyckerAdaper adaper=new RecyckerAdaper(this,list);
        //启动适配器
        recyclerView.setAdapter(adaper);
        //添加下划线
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.BLUE)

                .build());
        //接口回调的方法，用来toast用的4.调用定义的构造方法 来完成接口回调 使用adaper
        adaper.setLLiencli(new RecyckerAdaper.Sitenner() {
            @Override
            public void click(View view, int position) {
                Toast.makeText(MainActivity.this,"wahahhaa",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void Longclick(View view, int position) {
                Toast.makeText(MainActivity.this,"niahhahahha",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void refresh() {
        spingview.setType(SpringView.Type.FOLLOW);
        spingview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(),"下拉刷新中",Toast.LENGTH_SHORT).show();
                // list.clear();
                // 网络请求;
                // mStarFragmentPresenter.queryData();
                //一分钟之后关闭刷新的方法
                finishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                Toast.makeText(getApplicationContext(),"玩命加载中...",Toast.LENGTH_SHORT).show();
                finishFreshAndLoad();
            }
        });

    }

    /**
     * 关闭加载提示
     */
    private void finishFreshAndLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                spingview.onFinishFreshAndLoad();
            }
        }, 1000);
    }


}
