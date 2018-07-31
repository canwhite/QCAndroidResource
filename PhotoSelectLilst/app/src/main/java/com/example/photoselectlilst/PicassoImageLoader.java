package com.example.photoselectlilst;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PicassoImageLoader  implements ImageLoader{


    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)
                .load(Uri.fromFile(new File(path)))
                //.memoryPolicy(MemoryPolicy.NO_CACHE)//不缓存本地图片
                .resize(width, height)
                .centerInside()
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)
                .load(Uri.fromFile(new File(path)))
                .resize(width, height)
                .centerInside()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }



}
