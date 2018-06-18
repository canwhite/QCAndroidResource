package activitytest.example.com.litepaltest;

import android.database.Cursor;
import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.litepal.util.BaseUtility;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建按钮
        Button createDatabase = (Button)findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //这个就相当于创建了数据库，和关系映射表了,添加新表的时候把版本号改为2，然乎再调用此方法
                Connector.getDatabase();

            }
        });

        //添加数据
        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //book
                Book book = new Book();
                book.setName("The Da Vinci Code");
                book.setAuthor("Dan Brown");
                book.setPages(454);
                book.setPrice(16.95);
                book.setPress("Unknow");
                book.save();


            }
        });

        //更新数据
        Button updateData = (Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //两种可以更新的情况
                //1.先添加，确定存在了就可以更新
                //2.查出来的表示已存在，可以添加


                /*

                //对当下存储的值进行更改
                Book book = new Book();
                book.setName("The Lost Sysbol");
                book.setAuthor("Dan Brown");
                book.setPages(510);
                book.setPrice(19.95);
                book.setPress("Unknow");
                book.save();
                book.setPrice(10.99);
                book.save();
                */

                //上边的方法只能对当下存储的值进行更改限制性比较大，接下来我们使用一种更加灵巧的更新方式

                Book book = new Book();
                book.setPrice(14.45);
                book.setPress("Anchor");
                //这个updateAll就相当where
                book.updateAll("name = ? and author = ?","The Lost Sysbol","Dan Brown");

                /*
                一个知识点，当我们new出Book的时候，实际上那些值都已经有默认值了，例如int的0
                那我们如果想更新默认值的时候怎么做呢，setPrice(0)肯定是不可以的，因为即使不调用这行代码，它本身也是0
                 */
                /*
                Book book = new Book();
                book.setToDefault("pages");
                book.updateAll();
                */


            }
        });

        //删除数据
        Button deleteData = (Button)findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据
                //如果deleteAll函数不指定参数，它就是删除所有
                DataSupport.deleteAll(Book.class,"price < ?","15");
            }
        });


        //查询数据
        Button queryData = (Button)findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //查询所有数据
                List<Book> books = DataSupport.findAll(Book.class);
                for (Book book:books){

                    Log.d(TAG, "book name is " + book.getName());

                }


                /*

                //查询book表中的第一条数据

                Book firstBook = DataSupport.findFirst(Book.class);

                //查询book表中的最后一条数据
                Book lastBook = DataSupport.findLast(Book.class);

                //select() 查询某几列的值
                List<Book> cellBooks = DataSupport.select("name","author").find(Book.class);

                //where() 用于指定查询的约束条件
                List<Book> cellBooks2 = DataSupport.where("pages > ?","400").find(Book.class);

                //order() 按照指定的方式排序,desc降序，asc升序
                List<Book> cellBooks3 = DataSupport.order("price desc").find(Book.class);

                //limit() 用于指定查询结果的数量
                List<Book> cellBooks4 = DataSupport.limit(3).find(Book.class);

                //offset() 方法，用于指定查询结果的偏移量，本身是前三条，加了offset之后，是2，3，4条
                List<Book> cellBooks5 = DataSupport.limit(3).offset(1).find(Book.class);

                //连缀组合  ,查询11-20条满足页数大于400这个条件的name,author,pages这三列数据，并将查询结果按照页数升序排列
                List<Book> cellBooks6 = DataSupport.select("name","author","pages").where("pages > ?","400").order("pages").limit(10).find(Book.class);

                //也支持原生查询
                Cursor c = DataSupport.findBySQL("select * from Book where pages > ? and price < ?","400","20");

                */



            }
        });


    }
}
