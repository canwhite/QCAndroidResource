package activitytest.example.com.notificationtest;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        /*
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //这个id为1，是和前边照应着的
        manager.cancel(1);
        */


    }
}
