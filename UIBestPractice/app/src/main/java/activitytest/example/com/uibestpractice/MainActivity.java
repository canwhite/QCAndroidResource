package activitytest.example.com.uibestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         //初始化信息数据
        initMsgs();
        //输入框
        inputText = (EditText)findViewById(R.id.input_text);
        //发送按钮
        send = (Button)findViewById(R.id.send);
        //列表
        msgRecyclerView = (RecyclerView)findViewById(R.id.msg_recycler_view);

        //RecyclerView 需要设置layoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        //然后通过适配器设置数据
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = inputText.getText().toString();
                //判断字符串长度不为空的时候执行
                if (!"".equals(content)){
                    Msg msg = new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    //adapetr，当前有新信息的时候刷新list中的显示
                    adapter.notifyItemInserted(msgList.size() - 1);
                    //将listView定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    //清空输入框中的内容
                    inputText.setText("");

                }
            }
        });









    }

    //初始化数据
    private void initMsgs(){

        Msg msg1 = new Msg("Hello guy",Msg.TYPE_RECEIVED);
        msgList.add(msg1);

        Msg msg2 = new Msg("Hello ,Who is that?",Msg.TYPE_SEND);
        msgList.add(msg2);

        Msg msg3 = new Msg("This is Tom . Nice talking to you ",Msg.TYPE_RECEIVED);
        msgList.add(msg3);

    }

}
