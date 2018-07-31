package com.example.materialtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private Context mContext;
    private List<Fruit> mFruitList;

    //adapter的构造函数，主要是绑定
    public FruitAdapter(List<Fruit> fruitList){

        mFruitList = fruitList;

    }



    //viewHolder的构造函数，主要是控件的绑定
    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view;
            fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
            fruitName = (TextView)view.findViewById(R.id.fruit_name);
        }
    }



    //和viewHoler的绑定
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //得到父类context
        //这个mConnext主要是为了后边glide使用
        if (mContext == null){
            mContext = parent.getContext();
        }
        //通过父类活动绑定cardView
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        return  new ViewHolder(view);

    }
    //cell for row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Fruit fruit = mFruitList.get(position);

        holder.fruitName.setText(fruit.getName());
        /*
        Glide的使用
        .with .load .into 三个方法串行，没有关联
        with方法可以写入 context ， activity , fragment
        load 方法，可以是本地路径，url，或者资源id
        into就是某个控件

        //glide进行了非常复杂的逻辑，其中就包含图片压缩

         */
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);


    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }


}
