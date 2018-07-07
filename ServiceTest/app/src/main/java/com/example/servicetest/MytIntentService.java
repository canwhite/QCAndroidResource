package com.example.servicetest;

        import android.app.IntentService;
        import android.content.Intent;
        import android.support.annotation.Nullable;
        import android.util.Log;

public class MytIntentService extends IntentService {

    private static final String TAG = "MytIntentService";


    //提供一个无参构造函数，在其内部调用有参构造函数
    public MytIntentService(){

        super("MytIntentService");//调用父类的有参构造函数

    }

    //在这里处理一些具体的逻辑，封装好了
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG, "Thread id is " + Thread.currentThread().getId());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy ececuted");


    }
}
