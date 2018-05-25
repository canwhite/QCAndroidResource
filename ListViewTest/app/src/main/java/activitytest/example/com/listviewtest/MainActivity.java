package activitytest.example.com.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] data = {"apple","banana","orange","watermelon","pear","grape","cherry","apple","banana","orange","watermelon","pear","grape","cherry","apple","banana","orange","watermelon","pear","grape","cherry"};
    //fruitData存放的数组
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //数据具体化
        initFruits();

        //这个方法是默认方法，下边是我们自写的
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,data);

        FruitAdapter adapter = new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruitList);


        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //listView的点击事件

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        
    }
    private  void initFruits(){


        Fruit apple = new Fruit("Apple",R.drawable.fruit);
        fruitList.add(apple);


        Fruit banana = new Fruit("Banana",R.drawable.fruit);
        fruitList.add(banana);


    }




}