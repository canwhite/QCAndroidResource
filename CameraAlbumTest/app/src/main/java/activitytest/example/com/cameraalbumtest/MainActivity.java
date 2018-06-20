package activitytest.example.com.cameraalbumtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.net.URI;

import static android.Manifest.*;

/*
   相册
   desc:先发出在运行时打开相册的许可请求，如果成功，再打开相册，因为是隐式打开，所以根据成功的返回值就可以得到图片地址，最后就可以展示图片
   当然不同的sdk版本，获取的地址的方法也有些区别

 */


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public  static  final  int TAKE_PHOTO = 1;
    public  static  final  int CHOOSE_PHOTO = 2;


    private ImageView picture;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button takePhoto = (Button)findViewById(R.id.take_photo);
        Button chooseFromAlbum = (Button)findViewById(R.id.choose_from_album);
        picture = (ImageView)findViewById(R.id.picture);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //创建file对象，用于存储拍照后的图片
                //存放在sd卡的应用关联缓存目录
                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                try{

                    if (outputImage.exists()){outputImage.delete();}
                    outputImage.createNewFile();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //上边得到路径是为了唯一标志符
                if (Build.VERSION.SDK_INT >= 24){
                    //我们知道Uri是和路径关联的
                    //第二个参数是任意唯一字符串
                    /*
                        和内容提供器类似的机制，注意注册

                     */

                    imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.cameraalbumtest.fileprovider",outputImage);

                }else{

                    imageUri = Uri.fromFile(outputImage);
                }

                //启动相机程序
                //指定action，跳转到指定action的活动
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //放上路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                //反向传值
                startActivityForResult(intent,TAKE_PHOTO);



            }
        });


        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先判断是否获得了请求相册的许可
                //ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) 返回一个结果

                /*
                    运行时请求许可

                 */

                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {

                    // 运行时的结果请求
                    ActivityCompat.requestPermissions(MainActivity.this,new  String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                }else{

                    openAlbum();

                }


            }
        });
    }



    //请求许可的反馈
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case 1:
                //grantResults是一个数组，第一个是做判断的标准
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    openAlbum();
                }else{

                    //如果拒绝，就显示拒绝请求的提示栏目
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }


    }

    //打开相册
    private void  openAlbum(){

        //根据action找到内置的相册活动
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            //反向传值成功之后的判断
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){

                    //将拍摄的照片显示出来
                    try {
                        //上边是内容提供者，这里通过getContentResolver获取
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));


                        picture.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    }

                }
                break;


            //反向传值成功之后的判断

            case CHOOSE_PHOTO:



                if (resultCode == RESULT_OK){


                    if (Build.VERSION.SDK_INT >= 19){



                        Log.d(TAG, "onActivityResult: 1");
                        //4.4及以上系统使用这个方法处理
                        handleImageOnKitKat(data);

                    }else{

                        Log.d(TAG, "onActivityResult: 2");

                        //4.4及以下系统使用这个方法处理
                        handleImageBeforeKitKat(data);


                    }
                }
                break;

            default:
                break;
        }
    }
    @TargetApi(19)
    //4.4及以上系统用这个
    /*
        目的是为了得到图片地址，然后方便显示

     */

    private  void  handleImageOnKitKat(Intent data){

        String imagePath = null;
        Uri uri = data.getData();


        //如果是document类型的Uri，则通过document id 处理

        if (DocumentsContract.isDocumentUri(this,uri)){


            String docId = DocumentsContract.getDocumentId(uri);


            //如果Uri的authority是media格式的化，document id 还需要进一步的解析

            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                Toast.makeText(this,"back to main 1",Toast.LENGTH_SHORT).show();
                //解析出数据格式的id
                String id = docId.split(":")[1];
                String selection  = MediaStore.Images.Media._ID + "=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Toast.makeText(this,"back to main 2",Toast.LENGTH_SHORT).show();
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);


            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);


        }else if ("file".equalsIgnoreCase(uri.getScheme())){

            //如果是file类型的Uri，直接获取图片路径即可
            Toast.makeText(this,"back to main 4",Toast.LENGTH_SHORT).show();
            imagePath = uri.getPath();

        }


        //根据图片路径显示图片
        displayImage(imagePath);





    }

    //4.4及以下系统用这个
    private void  handleImageBeforeKitKat(Intent data){

        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);



    }

    //显示图片



    private  void displayImage(String imagePath){

        Toast.makeText(this,"back to main d",Toast.LENGTH_SHORT).show();
        if (imagePath != null){
            Toast.makeText(this,"back to main b",Toast.LENGTH_SHORT).show();
            //解码成一个位图文件路径。如果指定的文件名是空的,或无法解码成一个位图,函数返回null。



//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//
//            File file = new File(imagePath);
//            InputStream inputStream = null;
//            Bitmap bitmap = null;
//            //问题是这里边都没走
//            try {
//                Log.d(TAG, "displayImage1: " + bitmap);
//                Log.d(TAG, "displayImage2: " + imagePath);
//                inputStream = new FileInputStream(file);
//                bitmap = BitmapFactory.decodeStream(inputStream);
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            Bitmap bitmap = BitmapHelper.decodeSampledBitmapFromFile(imagePath, 200, 200);
            // 如果图片的旋转角度不为0，则需要将bitmap进行对应角度的旋转确保其正确显示
            bitmap = BitmapHelper.rotateBitmapByDegree(bitmap, BitmapHelper.getBitmapDegree(imagePath));
            // 将bitmap显示到ImageView上
            Log.d(TAG, "displayImage: " +  bitmap);
            Log.d(TAG, "displayImage: " +imagePath);
            picture.setImageBitmap(bitmap);

        }else{

            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }



    }



    //得到image的地址
    private  String getImagePath(Uri uri,String selection){

        String path = null;
        //通过Uri和selection获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){

            if (cursor.moveToNext()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }




}
