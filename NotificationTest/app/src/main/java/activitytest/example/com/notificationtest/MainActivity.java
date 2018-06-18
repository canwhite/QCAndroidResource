package activitytest.example.com.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice = (Button)findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.send_notice:

                Intent intent = new Intent(this,NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivities(this,0, new Intent[]{intent},0);




                //setAutoCancel  通知栏的通知信息取消，也可以在pendingIntent中进行取消操作
                //setSound 是允许通知声音，提供一个音频路径
                //setVibrate 设置震动提醒，一开始就震动，不静止，然后震动一秒，停止一秒，依次类推
                //setLights 设置led灯亮暗，参数一：灯的颜色 参数二：亮 参数三：暗
                //.setDefaults(NotificationCompat.DEFAULT_ALL)默认铃声和震动
                // 设置大图片 .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.big_image)))
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification= new NotificationCompat.Builder(this)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text klsfklsjdfklsdjkfjsklfjkdsjfkldsjfksdjfksjdkfjsdklfjlsd sdjfsdjkfsdkfjkddsfjsdjfsdjkf")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                        .setVibrate(new long[]{0,1000,1000,1000})
                        .setLights(Color.GREEN,1000,1000)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("sfjhsdjfhdjshfjdshfjhsdjfhjkshfjshfjshfjksdhfjkdshfjdshfjsdhfjsdhfjsdhfjksdhfjsdhfjsdhfjdks"))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();

                manager.notify(1,notification);

                break;
            default:
                break;

        }

    }
}
