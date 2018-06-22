package activitytest.example.com.playaudiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //可以在声明的时候直接初始化
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //三个按钮
        Button play = (Button)findViewById(R.id.play);
        Button pause = (Button)findViewById(R.id.pause);
        Button stop = (Button)findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);


        //动态获取写入权限
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


        }else{

            //初始化
            initMediaPlayer();


        }
    }

    private void initMediaPlayer(){


        try {
            //在sd卡的根目录下放一个music文件
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件的路径
            mediaPlayer.prepare();//让播放器进入准备状态


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //动态许可请求返回函数


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case 1:


                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    initMediaPlayer();

                }else{

                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();//没有权限我们什么也干不成，显示完之后就关掉就可以了

                }




                break;

            default:
        }


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.play:
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();//开始播放

                }
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()){
                    //暂停播放
                    mediaPlayer.pause();
                }
                break;

            case R.id.stop:
                if (mediaPlayer.isPlaying()){
                    //停止播放
                    mediaPlayer.reset();
                    initMediaPlayer();

                }
                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
