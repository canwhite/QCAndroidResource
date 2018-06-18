package activitytest.example.com.uibestpractice;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> mMsgList;

    //写一个ViewHolder类，里边对ViewHolder进行初始化，绑控件
    static public class ViewHolder extends RecyclerView.ViewHolder {

        //两个线性布局
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        public ViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);

        }
    }

    //适配器的构造函数,数据初始化
    public MsgAdapter(List<Msg> msgList){

        mMsgList = msgList;

    }


    //接下来是RecyclerView绑定的协议文件



    @NonNull
    @Override


    //绑布局
    public MsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);

        //上边写ViewHolder的构造在这里实现
        return new ViewHolder(view) ;
    }

    @Override
    //单个item绑数据,根据数据判断对界面做一些操作
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        //接受信息
        if (msg.getType() == Msg.TYPE_RECEIVED){
            //如果是收到信息，则显示左边的信息布局，将右边的信息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }
        //发送数据
        else if(msg.getType() == Msg.TYPE_SEND){

            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());

        }
    }

    @Override
    //item数据
    public int getItemCount() {
        return mMsgList.size();
    }


}
