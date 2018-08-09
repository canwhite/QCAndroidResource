package com.example.logutiltest;

import android.util.Log;

public class LogUtil {

    public  static  final  int VERBOSE = 1;
    public  static  final  int DEBUG = 2;
    public  static  final  int INFO = 3;
    public  static  final  int WARN = 4;
    public  static  final  int ERROR = 5;
    public  static  final  int NOTHING = 6;

    //如果上架的时候不想要结果显示，可以把等级调到最高,即NOTHING，就不会把日志信息暴露出来了
    public  static  final  int level = VERBOSE;


    public static  void  v(String tag, String msg){

        if (level <= VERBOSE ){
            Log.v(tag,"vvvvvvvvvv:" +msg);
        }
    }

    public  static void  d(String tag, String msg){

        if (level <= DEBUG){
            Log.d(tag,"dddddddddd:"+ msg);
        }

    }


    public static  void i(String tag,String msg){

        if (level <= INFO){
            Log.i(tag,"iiiiiiiiii:"+msg);
        }

    }

    public  static  void w(String tag, String msg){

        if (level <=  WARN){

            Log.w(tag, "wwwwwwwwww:"+ msg);

        }

    }


    public static  void e(String tag, String msg){

        if (level <= ERROR){
            Log.d(tag, "eeeeeeeeee:" + msg);

        }

    }



}
