package com.example.ericzhang.callbacktest;



//为了防止在子线程中数据请求方法会在服务器没有响应之前执行结束，拿不到数据，所以我们写一个接口回调，类似于ios的block
public interface QCCallBack {

    void onFinish(String response);

    void onError(Exception e);

}








