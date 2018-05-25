package activitytest.example.com.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initFruits();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        //使用的时候可以把item的宽度改变小一点
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        //网格   基础用法，3表示三列
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);


        /*
        //瀑布流，3表示三列，垂直布局，我们在这里需要借助下边的getRandomLengthName方法，把字符串长度设置的不一样长，来体现瀑布流的效果
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        */

        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

    }


    private void initFruits(){


        Fruit apple = new Fruit("Apple",R.drawable.fruit);
        fruitList.add(apple);
        Fruit apple2 = new Fruit("Apple2",R.drawable.fruit);
        fruitList.add(apple2);
        Fruit apple3 = new Fruit("Apple3",R.drawable.fruit);
        fruitList.add(apple3);
        Fruit apple4 = new Fruit("Apple4",R.drawable.fruit);
        fruitList.add(apple4);
        Fruit apple5 = new Fruit("Apple5",R.drawable.fruit);
        fruitList.add(apple5);
        Fruit banana = new Fruit("Banana",R.drawable.fruit);
        fruitList.add(banana);
        Fruit banana2 = new Fruit("Banana2",R.drawable.fruit);
        fruitList.add(banana2);
        Fruit banana3 = new Fruit("Banana3",R.drawable.fruit);
        fruitList.add(banana3);
        Fruit banana4 = new Fruit("Banana4",R.drawable.fruit);
        fruitList.add(banana4);
        Fruit banana5 = new Fruit("Banana5",R.drawable.fruit);
        fruitList.add(banana5);

        Fruit orange = new Fruit("Orange",R.drawable.fruit);
        fruitList.add(orange);




        /*
        //瀑布流测试使用
        Fruit apple = new Fruit(getRandomLengthName("Apple"),R.drawable.fruit);
        fruitList.add(apple);
        Fruit apple2 = new Fruit(getRandomLengthName("Apple2"),R.drawable.fruit);
        fruitList.add(apple2);
        Fruit apple3 = new Fruit(getRandomLengthName("Apple3"),R.drawable.fruit);
        fruitList.add(apple3);
        Fruit apple4 = new Fruit(getRandomLengthName("Apple4"),R.drawable.fruit);
        fruitList.add(apple4);
        Fruit apple5 = new Fruit(getRandomLengthName("Apple5"),R.drawable.fruit);
        fruitList.add(apple5);
        Fruit banana = new Fruit(getRandomLengthName("Apple6"),R.drawable.fruit);
        fruitList.add(banana);
        Fruit banana2 = new Fruit(getRandomLengthName("Apple7"),R.drawable.fruit);
        fruitList.add(banana2);
        Fruit banana3 = new Fruit(getRandomLengthName("Apple8"),R.drawable.fruit);
        fruitList.add(banana3);
        Fruit banana4 = new Fruit(getRandomLengthName("Apple9"),R.drawable.fruit);
        fruitList.add(banana4);
        Fruit banana5 = new Fruit(getRandomLengthName("Apple10"),R.drawable.fruit);
        fruitList.add(banana5);
        */
    }

    // 这个方法主要是为了快速的生成一个随机的字符串
    private String getRandomLengthName(String name){

        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++){

            builder.append(name);
        }
        return  builder.toString();

    }




}
