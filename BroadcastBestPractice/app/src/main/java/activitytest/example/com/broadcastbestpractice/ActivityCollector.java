package activitytest.example.com.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activityies = new ArrayList<>();

    //添加活动，
    public  static  void  addActivity(Activity activity){

        activityies.add(activity);

    }
    //移除活动
    public  static  void removeActivity(Activity activity){

        activityies.remove(activity);

    }
    //清除所有
    public  static  void  finishALL(){

        for (Activity activity : activityies){

            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }






}
