package com.byx.zz.springviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

/**
 * Created by zb on 2018/7/31.
 */

public class RecyckerAdaper extends RecyclerView.Adapter<RecyckerAdaper.ViewHolder> {

    private Context context;//初始化上下文
    public List list; //list集合 这个list集合没有参数
    public int itemwidth; //定义一个宽
    //重写一个有参的方法
    public RecyckerAdaper(Context context, List list) {
        //上下文和集合
        this.context = context;
        this.list = list;
        //获取屏幕的高和宽
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        //得到的每个item的宽度  一行三个item
        itemwidth=width/3;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        View view= LayoutInflater.from(context).inflate(R.layout.layout_item,null);
        // 返回ViewHolder的方法
        return new ViewHolder(view);
    }

    @Override
    //绑定数据
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Glide来加载图片
        Glide.with(context).load(list.get(position)).into(holder.iv);

        //设置一个默认的图片
        holder.iv.setImageResource(R.mipmap.ic_launcher);
        holder.tv.setText(position+"");
        //动态的来设置高宽的
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) holder.iv.getLayoutParams();
        //给了一个指定的高
        int itemheight=300;
        //最大限度是500
        itemheight =new Random().nextInt(500);
        //判断这个高小于300的话
        if(itemheight<300)
        {
            itemheight=300;
        }
        //设置宽度
        params.width=itemwidth;
        //设置高度
        params.height=itemheight;
        //把设置好的高和宽设置给图片
        holder.iv.setLayoutParams(params);
        //给图片添加点击事件
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3.调用构造方法里面的参数调用写的方法
                if(sienclic!=null)
                {
                    sienclic.click(v,position);
                }
            }
        });
        //长按事件
        holder.iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sienclic.Longclick(v,position);
                return false;
            }
        });
    }

    @Override
    //time的数量
    public int getItemCount() {
        //返回集合的长度
        return list.size();
    }


    //定义的viewholder类
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv;
        //holder类
        public ViewHolder(View itemView) {
            super(itemView);
            //初始化控件
            iv= (ImageView) itemView.findViewById(R.id.iv);
            tv= (TextView) itemView.findViewById(R.id.tvv);
        }
    }
    //定义的接口回调
    private   Sitenner sienclic;
    //接口回调的步骤 1.定义一个接口
    //2.设置一个构造方法
    public   void setLLiencli(Sitenner sienclic){

        this.sienclic=sienclic;
    }
    //1.定义接口 写俩个方法
    public interface  Sitenner{
        public  void click(View view,int position);
        public  void Longclick(View view,int position);
    }
}
