package com.example.photoselectlilst;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
//import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //动态请求权限

        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);




        initView();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener((ImagePickerAdapter.OnRecyclerViewItemClickListener) this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }



    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //添加图片
                showAddPicDialog();
                break;
            default:
                //预览图片
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }


    private void showAddPicDialog() {
//        dialog = new Dialog(this);
//        View v = LayoutInflater.from(this).inflate(R.layout.dialog, null);
//        dialog.setContentView(v);
//        dialog.show();
//        //打开相机拍照
//        v.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                Intent intent = new Intent(MainActivity.this, ImageGridActivity.class);
//                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
//                startActivityForResult(intent, REQUEST_CODE_SELECT);
//            }
//        });
//        //打开相册选择图片
//        v.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                Intent intent1 = new Intent(MainActivity.this, ImageGridActivity.class);
//                startActivityForResult(intent1, REQUEST_CODE_SELECT);
//            }
//        });

        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
        Intent intent1 = new Intent(MainActivity.this, ImageGridActivity.class);
        startActivityForResult(intent1, REQUEST_CODE_SELECT);

    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                //1、不压缩直接显示
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
                //2、开启子线程压缩后再显示
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (ImageItem item : images) {
//                            String sourceImagePath = item.path;
//                            File file = new File(item.path);
//                            String targetImagePath = getExternalFilesDir(null).getAbsolutePath() + "/Pic/" + file.getName();
//                            boolean save = PicCompressUtil.imageCompress(sourceImagePath, targetImagePath, 200);//压缩程度
//                            if (save) {
//                                Message message = Message.obtain();
//                                message.obj = targetImagePath;
//                                message.what = 1;
//                                handler.sendMessage(message);
//                            }
//                        }
//                    }
//                }).start();
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                ImageItem item = new ImageItem();
                item.path = (String) msg.obj;
                selImageList.add(item);
                //这里我是压缩一张，显示一张（也可以设置一个ProgressDialog待全部压缩完后，一起显示）
                adapter.setImages(selImageList);
            }
        }
    };

    /**
     * 上传图片文件
     */
    public void upLoadPic(View view) {
        Map<String, File> fileMap = new HashMap<>();
        List<ImageItem> list = adapter.getImages();
        for (ImageItem item : list) {
            fileMap.put(new File(item.path).getName(), new File(item.path));
        }
        //然后上传文件的map
    }
}



}
