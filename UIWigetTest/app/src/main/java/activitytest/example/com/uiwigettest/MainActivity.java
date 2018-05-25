package activitytest.example.com.uiwigettest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //匿名式按钮点击事件
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里加点击逻辑


            }
        });
        */
        
        //实现接口的方式进行注册

        Button button = (Button)findViewById(R.id.button);

        //这一点加上去是为了收回键盘
        editText = (EditText) findViewById(R.id.edit_text);

        //进行图片内容的更改
        imageView = (ImageView)findViewById(R.id.image_view);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        button.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button:

                Log.d(TAG, "onClick: My Click");
                /*
                //键盘收起
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                //把EditText中的内容显示出来

                String inputText = editText.getText().toString();

                Toast.makeText(MainActivity.this,inputText,Toast.LENGTH_SHORT).show();

                */


                /*
                // 对图片内容进行更改
                imageView.setImageResource(R.drawable.img_2);
                */

                /*
                //圆形进度条的消失和出现
                if (progressBar.getVisibility() == View.GONE){

                    progressBar.setVisibility(View.VISIBLE);

                }else{

                    progressBar.setVisibility(View.GONE);

                }

                */


                /*
                //水平进度条的进度变化

                int progress = progressBar.getProgress();
                progress = progress + 10;
                progressBar.setProgress(progress);

                */


                /*

                //警示栏

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //标题
                dialog.setTitle("This is a Dialog");
                //内容
                dialog.setMessage("Something importment.");
                //可否取消属性,表示不能通过
                dialog.setCancelable(false);
                //确定按钮
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

                */

                //ProgressDialog

                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("This is ProgressDialog");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);//表示可以通过返回键取消
                progressDialog.show();


                break;

            default:
                break;

        }


    }

}
