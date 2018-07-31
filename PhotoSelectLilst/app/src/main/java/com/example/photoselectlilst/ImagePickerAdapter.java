package com.example.photoselectlilst;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ImagePickerAdapter extends  RecyclerView.Adapter<ImagePickerAdapter.SelectedPicViewHolder> {



private int maxImgCount;
private Context mContext;
private List<ImageItem> mData;
private LayoutInflater mInflater;
private OnRecyclerViewItemClickListener listener;
private boolean isAdded;   //是否额外添加了最后一个图片

public interface OnRecyclerViewItemClickListener {
    void onItemClick(View view, int position);
}

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add(new ImageItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        else return mData;
    }

    public ImagePickerAdapter(Context mContext, List<ImageItem> data, int maxImgCount) {
        this.mContext = mContext;
        this.maxImgCount = maxImgCount;
        this.mInflater = LayoutInflater.from(mContext);
        setImages(data);
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.list_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView iv_img;
    private int clickPosition;

    public SelectedPicViewHolder(View itemView) {
        super(itemView);
        iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
    }

    public void bind(int position) {
        //设置条目的点击事件
        itemView.setOnClickListener(this);
        //根据条目位置设置图片
        ImageItem item = mData.get(position);
        if (isAdded && position == getItemCount() - 1) {
//            iv_img.setImageResource(R.drawable.btn_add_photos);
            iv_img.setImageResource(R.drawable.ic_arrow_back);
            clickPosition = MainActivity.IMAGE_ITEM_ADD;
        } else {
            ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 600, 600);
            clickPosition = position;
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onItemClick(v, clickPosition);
    }

}
}