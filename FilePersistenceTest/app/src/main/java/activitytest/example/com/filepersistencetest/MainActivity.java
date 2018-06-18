package activitytest.example.com.filepersistencetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //拿到edit
        edit = (EditText)findViewById(R.id.edit);

        String inputTest = load();
        if (!TextUtils.isEmpty(inputTest)){
            edit.setText(inputTest);
            edit.setSelection(inputTest.length());
            Toast.makeText(this,"Restoring succeeded",Toast.LENGTH_SHORT).show();
        }



    }

    //在活动消失之前保存数据


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //把edit数据拿到
        String inputText = edit.getText().toString();
        save(inputText);
    }


    //这个是存储方法
    public void  save(String inputText)  {

        FileOutputStream out = null;
        BufferedWriter writer = null;


        try{
            //第一个参数是文件名，第二个参数是更新模式
            out = openFileOutput("data",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);

        }catch (IOException e){
            e.printStackTrace();

        }finally {
            try{
                if (writer != null){
                    writer.close();

                }
            }catch (IOException e){

                e.printStackTrace();
            }
        }
    }

    //这个是读取方法

    public String load(){

        FileInputStream in = null;
        BufferedReader reader = null;
        //这个我们来接受读取的数据
        StringBuilder content = new StringBuilder();

        try{

            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                content.append(line);
            }

        } catch (IOException e) {

            e.printStackTrace();

        }finally {

            if (reader != null){

                try{

                    reader.close();
                }catch (IOException e){

                    e.printStackTrace();
                }

            }


        }
        return content.toString();


    }









}
