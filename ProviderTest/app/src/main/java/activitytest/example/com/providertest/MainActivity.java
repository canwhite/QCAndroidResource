package activitytest.example.com.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.URI;
import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //添加数据
        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //添加数据
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name","A Clash Of Kings");
                values.put("author","George Martin");
                values.put("pages",1040);
                values.put("price",22.85);

                //只需要两个参数： 一个 uri ，一个values
                Uri newUri = getContentResolver().insert(uri,values);
                //通过uri可以拿到id，然后id到后边更新的时候用
                newId = newUri.getPathSegments().get(1);


            }
        });

        //查询数据
        Button queryData = (Button)findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if (cursor != null){

                    while (cursor.moveToNext()){

                        String name = cursor.getString(cursor.getColumnIndex("name"));

                        Log.d(TAG, "book name id " + name);



                    }

                    cursor.close();

                }

            }
        });


        //更新数据
        Button updateData = (Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                ContentValues values = new ContentValues();
                values.put("name","A Storm of Swords");
                values.put("pages",1219);
                values.put("price",50.55);
                getContentResolver().update(uri,values,null,null);
            }
        });




        //删除数据
        Button deleteData = (Button)findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                getContentResolver().delete(uri,null,null);

            }
        });















    }
}
