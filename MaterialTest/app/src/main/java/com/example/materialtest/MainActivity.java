package com.example.materialtest;

import android.drm.DrmStore;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.Toolbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //滑动菜单
    private  DrawerLayout mDrawerLayout;
    private  Fruit[] fruits = {

            new Fruit("Orange",R.drawable.ningmeng),
            new Fruit("Orange",R.drawable.ningmeng),
            new Fruit("Orange",R.drawable.ningmeng),
            new Fruit("Orange",R.drawable.ningmeng),
            new Fruit("Orange",R.drawable.ningmeng),
            new Fruit("Orange",R.drawable.ningmeng),
    };
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;
    //加个刷新
    private SwipeRefreshLayout swipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = null;
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);


        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);

        /*
        //给toolbar设置左侧返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
         */

        //toolbar最左侧的按钮，叫做HomeAsUp按钮，它默认的图标是返回箭头，含义是返回上一个活动
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            //让导航栏显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置导航按钮图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //设置call为默认选中选项
        navView.setCheckedItem(R.id.nav_call);

        //对各个item进行监控
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //因为我们现在不需要在里边写内容，我们就在这里加一个关闭作用吧
                mDrawerLayout.closeDrawers();
                return  true;
            }
        });

        /*
           加一个悬浮按钮
           悬浮按钮和普通按钮的实现是一样的
         */
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(MainActivity.this, "FAB clicked",Toast.LENGTH_SHORT).show();

                //snackbar,一个更先进的的提示工具，可以进行一些逻辑

                Snackbar.make(v,"Data Deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


        /*

            加recyclerView

         */


        //获取随机水果，50个
        initFruits();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        //设置一个manager
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        //设置一个适配器
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);



        /*
            加刷新部分
         */

        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshFruits();

            }
        });
    }


    private void refreshFruits(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try{

                    //当前线程静止2秒，以便我们能看到效果
                    Thread.sleep(2000);


                }catch (InterruptedException e){

                    e.printStackTrace();


                }


                //主线程更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //重新创建随机数据
                        initFruits();
                        //刷新数据
                        adapter.notifyDataSetChanged();
                        //关闭刷新
                        swipeRefresh.setRefreshing(false);

                    }
                });
                
            }
        }).start();
    }


    //在水果数组范围内获取随机的水果
    private  void initFruits(){

        fruitList.clear();
        for (int i =0; i< 50 ; i++){

            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);

        }

    }


    /*
        添加了menu
     */

    //绑定
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar,menu);
        return  true;

    }

    //点击了选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            //homeAsUp按钮的id永远是android.R.id.home
            case android.R.id.home:
                //openDrawer()方法传入一个Gravity参数，为了保证行为和XML中定义的一致，我们传入GravityCompat.START
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
//            case R.id.back:
//                Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
//                break;

            case R.id.delete:
                Toast.makeText(this,"You Clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this,"You clicked Setting",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
        return true;

    }

}
