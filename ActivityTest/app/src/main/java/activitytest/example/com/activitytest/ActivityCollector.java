package activitytest.example.com.activitytest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    //创建一个list
    public static List<Activity> activities = new ArrayList<>();

    //添加活动
    public static void addActivity(Activity activity){

        activities.add(activity);

    }

    //移除活动
    public static void removeActivity(Activity activity){

        activities.remove(activity);

    }


    //停止所有活动
    public static void finishAll(){


        for (Activity activity:activities
             ) {


            if (!activity.isFinishing()){

                activity.finish();

            }
        }
    }
































}
