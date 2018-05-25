package activitytest.example.com.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {


    private List<Fruit> mFruitList;


    //viewHolder类即构造函数，主要是为了绑定控件
    static class ViewHolder extends RecyclerView.ViewHolder {

        View fruitView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            fruitView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }
    //适配器构造函数，主要是在控制类中传入数值
    public FruitAdapter(List<Fruit> fruitList){

        mFruitList = fruitList;

    }


    //recyclerView协议；上边viewHolder的构造函数，在这里使用，可以对控件进行操作，相当于oc中的didselect
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,parent,false);
        //ViewHolder holder = new ViewHolder(view);//这是不需要点击的方法

        /*
        添加点击事件
        */
        //给view添加点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(),"you clicked view   " + fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(),"you clicked image   " + fruit.getName(),Toast.LENGTH_SHORT).show();


            }
        });

        return  holder;

    }


    //绑定数据赋值，相当于oc中的cellforrow
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());

    }

    //item个数
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }




}


