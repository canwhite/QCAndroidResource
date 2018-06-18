package activitytest.example.com.uibestpractice;

public class Msg {

    //静态常量，既分配了内存又不能改变
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;
    private  String content;
    private  int type;

    //构造函数，初始化变量
    public Msg(String content, int type){

        this.content = content;
        this.type = type;

    }

    //外部获取的时候
    public  String getContent(){

        return  content;

    }
    public  int getType(){

        return  type;
    }












}
