package activitytest.example.com.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //通过构造函数，将数据名指定为BookStore.db，版本号指定为1,把版本号，改成2，表示我们有更新了
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);

        //创建按钮
        Button createDatabase = (Button)findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //构建出数据库之后，再调用read和write方法就可以创建数据库了
                //这个方法实际上还会返回一个database对象
                dbHelper.getWritableDatabase();

            }
        });

        //添加数据

        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //通过write加一个database对象
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                /*


                //开始组装第一组数据
                ContentValues values = new ContentValues();
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                //插入第一条数据
                db.insert("Book",null,values);

                //组装第二条数据前，先把数据清空
                values.clear();

                //重新组装第二组数据
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("Book",null,values);

                */

                //还是喜欢用sql语句添加数据
                db.execSQL("insert into Book (name,author,pages,price) values (?,?,?,?)",new String[]{"The Da Vinci Code","Dan Brown","454","16.96"});
                db.execSQL("insert into Book (name,author,pages,price) values (?,?,?,?)",new  String[]{"The Lost Symbol","Dan Brown","510","19.95"});



            }
        });

        //更新按钮
        Button updateData = (Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                /*
                ContentValues values = new ContentValues();
                values.put("price",10.99);
                //将名字是The Da Vinci Code 这本书的价格改为10.99
                db.update("Book",values,"name = ?",new String[]{"The Da Vinci Code"});
                */
                db.execSQL("update Book set price = ? where name = ?", new String[]{"10.99","The Da Vinci Code"});


            }
        });

        //删除按钮
        Button deleteButton = (Button)findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                /*
                //删除pages > 500 的那一行
                db.delete("Book","pages > ?",new  String[]{"500"});
                */

                db.execSQL("delete from Book where pages > ?",new  String[]{"500"});


            }
        });

        //查询数据

        Button queryButton = (Button)findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先拿到对象吧
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                /*
                //查询Book表中的所有数据
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                */

                Cursor cursor = db.rawQuery("select * from Book",null);

                //cursor相当于查询结果的数组，
                if (cursor.moveToFirst()){

                    do {
                        //当然也有getInt和getDouble
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        Log.d(TAG, "book name is " + name);
                    }while (cursor.moveToNext());

                }

            }
        });


    }
}
