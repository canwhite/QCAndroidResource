package activitytest.example.com.listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


//
public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId;

    public FruitAdapter(Context context, int TextViewResourceId, List<Fruit> objects){
        super(context,TextViewResourceId,objects);
        resourceId = TextViewResourceId;
    }


    @NonNull
    @Override

    //相当于tableView中对cell的处理，Fruit是model，还要处理view的复用问题
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获取当前项的fruit实例
        Fruit fruit = getItem(position);
        //如果view在缓存中存在，就不新建了，否则新建
        View view;
        ViewHolder viewHolder;


        if (convertView == null){
            //先理解为它是一个动态滑动的，所以不为view添加父布局，所以false
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = (TextView) view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);//将viewHolder存储在view中



        }else{

            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }
        /*
        //每次都获取控件的实例并设置，可以优化为如果创建就把实例存起来，下次用到再读取
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());

        */

        //再通过viewHolder统一设置内容
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());





        return  view;

    }
}
